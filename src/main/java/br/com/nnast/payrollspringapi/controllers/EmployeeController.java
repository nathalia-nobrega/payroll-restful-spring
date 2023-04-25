package br.com.nnast.payrollspringapi.controllers;

import br.com.nnast.payrollspringapi.entities.Employee;
import br.com.nnast.payrollspringapi.exceptions.EmployeeNotFoundException;
import br.com.nnast.payrollspringapi.repositories.EmployeeRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeController {
    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/employees")
    List<Employee> findAll() {
        return repository.findAll();
    }

    @PostMapping("/employees")
    Employee save(@RequestBody String name, String role) {
        return repository.save(new Employee(name, role));
    }

    @GetMapping("/employees/{id}")
    EntityModel<Employee> findOne (@PathVariable Long id) throws EmployeeNotFoundException {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

       /* return EntityModel.of(employee, linkTo(methodOn(EmployeeController.class).findOne(id).withSelfRel()),
                linkTo(methodOn(EmployeeController.class).findAll().withRel("employees"));*/
    }
    @PutMapping("/employees/{id}")
    Employee update(Employee employee, Long id) {
        return repository.findById(id)
                .map(emp -> {
                    emp.setName(employee.getName());
                    emp.setRole(employee.getRole());
                    return repository.save(emp);
                })
                .orElseGet(() -> {
                    employee.setId(id);
                    return repository.save(employee);
                });
    }

    @DeleteMapping ("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
