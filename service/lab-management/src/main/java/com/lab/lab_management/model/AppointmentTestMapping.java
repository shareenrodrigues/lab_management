package com.lab.lab_management.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "Appointment_Test_Mapping")
public class AppointmentTestMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AppointmentTestMappingID")
    private Long appointmentTestMappingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AppointmentID", referencedColumnName = "AppointmentID", nullable = false)
    @JsonBackReference  
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TestID", referencedColumnName = "TestID", nullable = false)
    private Test test;

    @Column(name = "TestStatus", nullable = false)
    private String testStatus;

    @OneToOne(mappedBy = "appointmentTestMapping", cascade = CascadeType.ALL)
    private TestResult testResult;

    public Long getId() {
        return appointmentTestMappingId;
    }

    public void setId(Long id) {
        this.appointmentTestMappingId = id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public String getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
    }

    public TestResult getTestResult() {
        return testResult;
    }

    public void setTestResult(TestResult testResult) {
        this.testResult = testResult;
    }


}
