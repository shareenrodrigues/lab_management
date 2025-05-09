package com.lab.lab_management.dtoModels;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentResponseDTO {
    public Long appointmentId;
    public String patientName;
    public Integer age;
    public BigDecimal height;
    public BigDecimal weight;
    public LocalDate appointmentDate;
    public LocalTime appointmentTime;
    public String appointmentStatus;
    public BigDecimal totalCost;
    public List<TestMappingDTO> testMappings;
    
}

