package com.example.leave.service;

import com.example.leave.dto.EmployeeRequest;
import com.example.leave.dto.EmployeeResponse;
import com.example.leave.entity.Employee;
import com.example.leave.exception.EmployeeNotFoundException;
import com.example.leave.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeResponse addEmployee(EmployeeRequest request) {
        Employee employee = new Employee(
                request.name(),
                request.email(),
                request.department()
        );

        return toResponse(employeeRepository.save(employee));
    }

    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return toResponse(employee);
    }

    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        employee.setName(request.name());
        employee.setEmail(request.email());
        employee.setDepartment(request.department());

        return toResponse(employeeRepository.save(employee));
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        employeeRepository.delete(employee);
    }

    private EmployeeResponse toResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment()
        );
    }
}
