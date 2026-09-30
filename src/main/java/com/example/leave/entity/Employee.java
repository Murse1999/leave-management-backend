package com.example.leave.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "姓名不可為空")
    @Size(max = 50, message = "姓名最多 50 個字")
    @Column(nullable = false, length = 50)
    private String name;

    @NotBlank(message = "Email 不可為空")
    @Email(message = "Email 格式不正確")
    @Size(max = 100, message = "Email 最多 100 個字")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "部門不可為空")
    @Size(max = 50, message = "部門最多 50 個字")
    @Column(nullable = false, length = 50)
    private String department;

    // JPA 需要無參數建構子
    public Employee() {
    }

    public Employee(String name, String email, String department) {
        this.name = name;
        this.email = email;
        this.department = department;
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}