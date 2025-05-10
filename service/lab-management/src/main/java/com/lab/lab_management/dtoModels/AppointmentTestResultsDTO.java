package com.lab.lab_management.dtoModels;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentTestResultsDTO {
      public Long appointmentId;
    public String patientName;
    public LocalDate date;
    public LocalTime time;
    public String appointmentStatus;
    public BigDecimal totalCost;

    public List<TestResultsDTO> testResults;
    
}
