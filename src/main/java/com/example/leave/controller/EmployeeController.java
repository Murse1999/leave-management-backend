package com.example.leave.controller;

import com.example.leave.dto.EmployeeRequest;
import com.example.leave.dto.EmployeeResponse;
import com.example.leave.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {

        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public EmployeeResponse addEmployee(
            @Valid @RequestBody EmployeeRequest request) {
        return employeeService.addEmployee(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(
            @PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
