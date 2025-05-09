package com.lab.lab_management.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Tests")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TestId")
    private Long testId;

    @Column(name= "TestName", nullable = false, unique = true)
    private String testName;

    @Column(name= "TestCode", nullable = false)
    private String testCode;

    @Column(name= "SampleType", nullable = false)
    private String sampleType;

    @Column(name="TestDetails", length = 500, nullable = false)
    private String testDetails;

    @Column(name= "Price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name= "EstimatedTime", nullable = false)
    private String estimatedTime;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AppointmentTestMapping> testMappings;

    // Constructors
    public Test() {}


    // Getters and Setters
    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestDetails() {
        return testDetails;
    }

    public void setTestDetails(String testDetails) {
        this.testDetails = testDetails;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setPreparationRequired(String testCode) {
        this.testCode = testCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

}
