package com.lab.lab_management.controller;

import com.lab.lab_management.model.Test;
import com.lab.lab_management.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/tests")
public class TestController {

    @Autowired
    private TestRepository testRepository;

    //Get all tests
    @GetMapping
    public ResponseEntity<List<Test>> getAllTests() {
        List<Test> tests = testRepository.findAll();
         tests.sort(Comparator.comparing(Test::getTestName, String.CASE_INSENSITIVE_ORDER));
        return ResponseEntity.ok(tests);
    }

    //Get test by ID
    @GetMapping("/{id}")
    public ResponseEntity<Test> getTestById(@PathVariable Long id) {
        return testRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
