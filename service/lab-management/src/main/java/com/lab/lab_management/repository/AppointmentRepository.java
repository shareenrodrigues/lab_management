package com.lab.lab_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lab.lab_management.model.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByUserUserId(Long userId);

    @EntityGraph(attributePaths = {
            "testMappings",
            "testMappings.test",
            "testMappings.testResult"
    })
    @Query("SELECT a FROM Appointment a")
    List<Appointment> findAllWithTestsAndResults();

}
