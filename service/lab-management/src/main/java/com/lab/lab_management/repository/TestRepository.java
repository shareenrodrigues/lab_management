package com.lab.lab_management.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lab.lab_management.model.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    
}
