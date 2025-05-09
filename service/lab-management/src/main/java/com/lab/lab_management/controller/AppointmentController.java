package com.lab.lab_management.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lab.lab_management.dtoModels.AppointmentRequestDTO;
import com.lab.lab_management.dtoModels.AppointmentResponseDTO;
import com.lab.lab_management.model.*;
import com.lab.lab_management.repository.AppointmentRepository;
import com.lab.lab_management.repository.AppointmentTestMappingRepository;
import com.lab.lab_management.repository.TestRepository;
import com.lab.lab_management.repository.UserRepository;
import com.lab.lab_management.service.AppointmentService;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentTestMappingRepository appointmentTestMappingRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @PostMapping("/createAppointment")
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentRequestDTO dto) {
        Appointment appointment = new Appointment();

        appointment.setPatientName(dto.patientName);
        appointment.setAge(dto.age);
        appointment.setHeight(dto.height);
        appointment.setWeight(dto.weight);
        appointment.setAppointmentDate(dto.appointmentDate);
        appointment.setAppointmentTime(dto.appointmentTime);
        appointment.setTotalCost(dto.totalCost);
        appointment.setAppointmentStatus("PENDING");

        // Link user
        User user = userRepository.findById(dto.userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        appointment.setUser(user);

        // Save appointment first
        Appointment saved = appointmentService.saveAppointment(appointment);

        // Link tests (if applicable)
        for (Long testId : dto.testIds) {
            Test test = testRepository.findById(testId)
                    .orElseThrow(() -> new RuntimeException("Test not found"));
            AppointmentTestMapping mapping = new AppointmentTestMapping();
            mapping.setAppointment(saved);
            mapping.setTest(test);
            mapping.setTestStatus("PENDING");
            appointmentTestMappingRepository.save(mapping);
        }

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByUser(@PathVariable Long userId) {
        List<AppointmentResponseDTO> appointments = appointmentService.getAppointmentsByUserId(userId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping()
    public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointments() {
        List<AppointmentResponseDTO> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/cancelAppointment/{id}")
    public ResponseEntity<Appointment> cancelAppointment(@PathVariable Integer id) {
        Appointment appt = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appt.setAppointmentStatus("CANCELLED");

        for (AppointmentTestMapping mapping : appt.getTestMappings()) {
            mapping.setTestStatus("CANCELLED");
        }

        Appointment updated = appointmentRepository.save(appt);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{appointmentId}/test/{testId}")
    public ResponseEntity<Void> deleteTestFromAppointment(@PathVariable Integer appointmentId,
            @PathVariable Long testId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        List<AppointmentTestMapping> mappings = appointment.getTestMappings();
        mappings.removeIf(mapping -> mapping.getTest().getTestId().equals(testId));

        appointmentRepository.save(appointment);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/rejectAppointment/{id}")
    public ResponseEntity<Appointment> rejectAppointment(@PathVariable Integer id) {
        Appointment appt = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appt.setAppointmentStatus("REJECTED");

        for (AppointmentTestMapping mapping : appt.getTestMappings()) {
            mapping.setTestStatus("REJECTED");
        }

        Appointment updated = appointmentRepository.save(appt);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/reschedule/{id}")
    public ResponseEntity<Appointment> rescheduleAppointment(@PathVariable Integer id,
            @RequestBody Map<String, String> payload) {
        Appointment appt = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appt.setAppointmentDate(LocalDate.parse(payload.get("appointmentDate")));
        appt.setAppointmentTime(LocalTime.parse(payload.get("appointmentTime")));
        appt.setAppointmentStatus("RESCHEDULED");

        return ResponseEntity.ok(appointmentRepository.save(appt));
    }

}
