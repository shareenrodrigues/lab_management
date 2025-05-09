package com.lab.lab_management.dtoModels;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;

public class AppointmentRequestDTO {
    public String patientName;
    public Integer age;
    public BigDecimal height;
    public BigDecimal weight;
    public LocalDate appointmentDate;
    public LocalTime appointmentTime;
    public BigDecimal totalCost;
    public Long userId;
    public List<Long> testIds;
}



