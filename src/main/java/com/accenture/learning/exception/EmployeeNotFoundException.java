package com.accenture.learning.exception;

public class EmployeeNotFoundException extends Exception {

    public EmployeeNotFoundException() {
        super("Employee not found");
    }
}
