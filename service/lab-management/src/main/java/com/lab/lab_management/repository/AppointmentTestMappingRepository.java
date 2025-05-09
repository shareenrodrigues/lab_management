package com.lab.lab_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lab.lab_management.model.AppointmentTestMapping;

@Repository
public interface AppointmentTestMappingRepository extends JpaRepository<AppointmentTestMapping, Long>{
    
}
