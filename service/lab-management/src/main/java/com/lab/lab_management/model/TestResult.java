package com.lab.lab_management.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Test_Results")
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TestResultID")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AppointmentTestMappingID", referencedColumnName = "AppointmentTestMappingID", nullable = false)
    private AppointmentTestMapping appointmentTestMapping;

    @Column(name = "ResultValue", nullable = false)
    private String resultValue;

    @Column(name = "ResultUnit", nullable = false)
    private String resultUnit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AppointmentTestMapping getAppointmentTestMapping() {
        return appointmentTestMapping;
    }

    public void setAppointmentTestMapping(AppointmentTestMapping appointmentTestMapping) {
        this.appointmentTestMapping = appointmentTestMapping;
    }

    public String getResultValue() {
        return resultValue;
    }

    public void setResultValue(String resultValue) {
        this.resultValue = resultValue;
    }

    public String getResultUnit() {
        return resultUnit;
    }

    public void setResultUnit(String resultUnit) {
        this.resultUnit = resultUnit;
    }

    
}
