package br.com.nnast.payrollspringapi.repositories;

import br.com.nnast.payrollspringapi.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
