package com.lab.lab_management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab.lab_management.dtoModels.AppointmentResponseDTO;
import com.lab.lab_management.dtoModels.TestMappingDTO;
import com.lab.lab_management.model.Appointment;
import com.lab.lab_management.model.Appointment.AppointmentStatus;
import com.lab.lab_management.repository.AppointmentRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository repository;

    public Appointment saveAppointment(Appointment appointment) {
        return repository.save(appointment);
    }

    public List<AppointmentResponseDTO> getAppointmentsByUserId(Long userId) {
        List<Appointment> appointments = repository.findByUserUserId(userId);

        return appointments.stream().map(appt -> {
            AppointmentResponseDTO dto = new AppointmentResponseDTO();
            dto.appointmentId = appt.getAppointmentId();
            dto.patientName = appt.getPatientName();
            dto.age = appt.getAge();
            dto.height = appt.getHeight();
            dto.weight = appt.getWeight();
            dto.appointmentDate = appt.getAppointmentDate();
            dto.appointmentTime = appt.getAppointmentTime();
            dto.appointmentStatus = appt.getAppointmentStatus();
            dto.totalCost = appt.getTotalCost();

            dto.testMappings = appt.getTestMappings().stream().map(mapping -> {
                TestMappingDTO tm = new TestMappingDTO();
                tm.testName = mapping.getTest().getTestName();
                tm.testCode = mapping.getTest().getTestCode();
                tm.price = mapping.getTest().getPrice();
                tm.testStatus = mapping.getTestStatus();
                tm.testId = mapping.getTest().getTestId();
                return tm;
            }).toList();

            return dto;
        }).toList();
    }

    public List<AppointmentResponseDTO> getAllAppointments() {
    List<Appointment> appointments = repository.findAll();
    return appointments.stream().map(appt -> {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.appointmentId = appt.getAppointmentId();
        dto.patientName = appt.getPatientName();
        dto.age = appt.getAge();
        dto.height = appt.getHeight();
        dto.weight = appt.getWeight();
        dto.appointmentDate = appt.getAppointmentDate();
        dto.appointmentTime = appt.getAppointmentTime();
        dto.appointmentStatus = appt.getAppointmentStatus();
        dto.totalCost = appt.getTotalCost();

        dto.testMappings = appt.getTestMappings().stream().map(mapping -> {
            TestMappingDTO tm = new TestMappingDTO();
            tm.testName = mapping.getTest().getTestName();
            tm.testCode = mapping.getTest().getTestCode();
            tm.price = mapping.getTest().getPrice();
            tm.testStatus = mapping.getTestStatus();
            tm.testId = mapping.getTest().getTestId();
            return tm;
        }).toList();

        return dto;
    }).toList();
}

}
