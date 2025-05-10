-- Create the database
CREATE DATABASE IF NOT EXISTS lab_management;
USE lab_management;


-- Users table
CREATE TABLE Users (
    UserID BIGINT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(255) NOT NULL,
    LastName VARCHAR(255) NOT NULL,
    Email VARCHAR(255) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    RoleName VARCHAR(255) NOT NULL
);


-- Tests table
CREATE TABLE Tests (
    TestId BIGINT AUTO_INCREMENT PRIMARY KEY,
    TestName VARCHAR(255) NOT NULL UNIQUE,
    TestCode VARCHAR(255) NOT NULL,
    SampleType VARCHAR(255) NOT NULL,
    EstimatedTime VARCHAR(255) NOT NULL,
    Price DECIMAL(10,2) NOT NULL,
    TestDetails VARCHAR(500) NOT NULL
);


-- Appointments table
CREATE TABLE Appointments (
    AppointmentID BIGINT AUTO_INCREMENT PRIMARY KEY,
    UserID BIGINT NOT NULL,
    PatientName VARCHAR(255) NOT NULL,
    Age INT NOT NULL,
    Height DECIMAL(38,2) NOT NULL,
    Weight DECIMAL(38,2) NOT NULL,
    AppointmentDate DATE NOT NULL,
    AppointmentTime TIME NOT NULL,
    AppointmentStatus VARCHAR(255) NOT NULL,
    TotalCost DECIMAL(38,2) NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);


-- Appointment_Test_Mapping table
CREATE TABLE Appointment_Test_Mapping (
    AppointmentTestMappingID BIGINT AUTO_INCREMENT PRIMARY KEY,
    AppointmentID BIGINT NOT NULL,
    TestID BIGINT NOT NULL,
    TestStatus VARCHAR(255) NOT NULL,
    FOREIGN KEY (AppointmentID) REFERENCES Appointments(AppointmentID),
    FOREIGN KEY (TestID) REFERENCES Tests(TestId)
);


-- Test_Results table
CREATE TABLE Test_Results (
    TestResultID INT AUTO_INCREMENT PRIMARY KEY,
    AppointmentTestMappingID BIGINT NOT NULL UNIQUE,
    ResultValue VARCHAR(255) NOT NULL,
    ResultUnit VARCHAR(255) NOT NULL,
    FOREIGN KEY (AppointmentTestMappingID) REFERENCES Appointment_Test_Mapping(AppointmentTestMappingID)
);
