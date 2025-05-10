package com.lab.lab_management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab.lab_management.dtoModels.AppointmentResponseDTO;
import com.lab.lab_management.dtoModels.AppointmentTestResultsDTO;
import com.lab.lab_management.dtoModels.TestMappingDTO;
import com.lab.lab_management.dtoModels.TestResultDTO;
import com.lab.lab_management.dtoModels.TestResultsDTO;
import com.lab.lab_management.exception.ResourceNotFoundException;
import com.lab.lab_management.model.Appointment;
import com.lab.lab_management.model.AppointmentTestMapping;
import com.lab.lab_management.repository.AppointmentRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository repository;

    public Appointment saveAppointment(Appointment appointment) {
        try{
            return repository.save(appointment);
        }
        catch(Exception ex){
            throw new RuntimeException(
                    "Failed to saveAppointment: " + ex.getMessage(), ex);
        }
        
    }

    public List<AppointmentResponseDTO> getAppointmentsByUserId(Long userId) {
        try{
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
        catch(Exception ex){
             throw new RuntimeException(
                    "Failed to getAppointmentsByUserId: " + ex.getMessage(), ex);
        }
    }

    public List<AppointmentResponseDTO> getAllAppointments() {
        try{
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
        catch(Exception ex){
             throw new RuntimeException(
                    "Failed to getAllAppointments: " + ex.getMessage(), ex);
        }
    }

    public AppointmentResponseDTO convertToResponseDTO(Appointment appointment) {
        try{
            AppointmentResponseDTO dto = new AppointmentResponseDTO();

        dto.appointmentId = appointment.getAppointmentId().longValue();
        dto.patientName = appointment.getPatientName();
        dto.age = appointment.getAge();
        dto.height = appointment.getHeight();
        dto.weight = appointment.getWeight();
        dto.appointmentDate = appointment.getAppointmentDate();
        dto.appointmentTime = appointment.getAppointmentTime();
        dto.appointmentStatus = appointment.getAppointmentStatus();
        dto.totalCost = appointment.getTotalCost();

        List<TestMappingDTO> testDTOs = new ArrayList<>();
        for (AppointmentTestMapping mapping : appointment.getTestMappings()) {
            TestMappingDTO t = new TestMappingDTO();
            t.testId = mapping.getTest().getTestId();
            t.testName = mapping.getTest().getTestName();
            t.testStatus = mapping.getTestStatus();

            if (mapping.getTestResult() != null) {
                TestResultDTO result = new TestResultDTO();
                result.resultValue = mapping.getTestResult().getResultValue();
                result.resultUnit = mapping.getTestResult().getResultUnit();
                t.result = result;
            }

            testDTOs.add(t);
        }

        dto.testMappings = testDTOs;
        return dto;
        }
        catch(Exception ex){
             throw new RuntimeException(
                    "Failed to convertToResponseDTO: " + ex.getMessage(), ex);
        }
    }

    public List<AppointmentTestResultsDTO> getAppointmentWithTestsAndResults(Long userId) {
        try {

            List<Appointment> appointments = repository.findByUserUserId(userId);

            return appointments.stream().map(appt -> {
                AppointmentTestResultsDTO dto = new AppointmentTestResultsDTO();
                dto.appointmentId = appt.getAppointmentId();
                dto.patientName = appt.getPatientName();
                dto.date = appt.getAppointmentDate();
                dto.time = appt.getAppointmentTime();
                dto.appointmentStatus = appt.getAppointmentStatus();
                dto.totalCost = appt.getTotalCost();

                dto.testResults = appt.getTestMappings().stream().map(mapping -> {
                    TestResultsDTO resultDTO = new TestResultsDTO();
                    resultDTO.testName = mapping.getTest().getTestName();
                    resultDTO.testCode = mapping.getTest().getTestCode();
                    resultDTO.price = mapping.getTest().getPrice();
                    resultDTO.testStatus = mapping.getTestStatus();

                    if (mapping.getTestResult() != null) {
                        resultDTO.resultValue = mapping.getTestResult().getResultValue();
                        resultDTO.resultUnit = mapping.getTestResult().getResultUnit();
                    }

                    return resultDTO;
                }).toList();

                return dto;
            }).toList();

        } catch (Exception ex) {
            throw new RuntimeException(
                    "Failed to getAppointmentWithTestsAndResults using userid: " + ex.getMessage(), ex);
        }
    }

    public List<AppointmentTestResultsDTO> getAllAppointmentWithTestsAndResults() {
        try {
            List<Appointment> appointments = repository.findAllWithTestsAndResults();

            if (appointments == null || appointments.isEmpty()) {
                throw new ResourceNotFoundException("No appointments found.");
            }

            return appointments.stream().map(appointment -> {
                AppointmentTestResultsDTO dto = new AppointmentTestResultsDTO();
                dto.appointmentId = appointment.getAppointmentId();
                dto.patientName = appointment.getPatientName();
                dto.date = appointment.getAppointmentDate();
                dto.time = appointment.getAppointmentTime();
                dto.appointmentStatus = appointment.getAppointmentStatus();
                dto.totalCost = appointment.getTotalCost();

                dto.testResults = appointment.getTestMappings().stream().map(mapping -> {
                    TestResultsDTO testDTO = new TestResultsDTO();
                    testDTO.testName = mapping.getTest().getTestName();
                    testDTO.testCode = mapping.getTest().getTestCode();
                    testDTO.price = mapping.getTest().getPrice();
                    testDTO.testStatus = mapping.getTestStatus();

                    if (mapping.getTestResult() != null) {
                        testDTO.resultValue = mapping.getTestResult().getResultValue();
                        testDTO.resultUnit = mapping.getTestResult().getResultUnit();
                    }

                    return testDTO;
                }).toList();

                return dto;
            }).toList();

        }catch (Exception ex) {
            throw new RuntimeException("Failed to getAllAppointmentWithTestsAndResults: " + ex.getMessage(), ex);
        }
    }

}
