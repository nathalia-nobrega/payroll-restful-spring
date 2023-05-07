package br.com.nnast.payrollspringapi.repositories;

import br.com.nnast.payrollspringapi.entities.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
