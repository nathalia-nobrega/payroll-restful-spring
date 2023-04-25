package br.com.nnast.payrollspringapi.controllers;

import br.com.nnast.payrollspringapi.assemblers.EmployeeModelAssembler;
import br.com.nnast.payrollspringapi.entities.Employee;
import br.com.nnast.payrollspringapi.exceptions.EmployeeNotFoundException;
import br.com.nnast.payrollspringapi.repositories.EmployeeRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeController {
    private final EmployeeRepository repository;
    private final EmployeeModelAssembler assembler;

    public EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }


    @GetMapping("/employees")
    public CollectionModel<EntityModel<Employee>> findAll() throws EmployeeNotFoundException {
        List<EntityModel<Employee>> employees = repository.findAll()
                .stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).findAll()).withSelfRel());
    }

    @PostMapping("/employees")
    public Employee save(@RequestBody String name, String role) {
        return repository.save(new Employee(name, role));
    }

    @GetMapping("/employees/{id}")
    public EntityModel<Employee> findOne(@PathVariable Long id) {
        return assembler.toModel(repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id)));
    }

    @PutMapping("/employees" + "/{id}")
    public Employee update(Employee employee, Long id) {
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

    @DeleteMapping("/employees" + "/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
