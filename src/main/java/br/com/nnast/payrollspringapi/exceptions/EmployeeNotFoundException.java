package br.com.nnast.payrollspringapi.exceptions;

public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException(Long id) {
        super("Employee with ID " + id + " was not found");
    }
}
