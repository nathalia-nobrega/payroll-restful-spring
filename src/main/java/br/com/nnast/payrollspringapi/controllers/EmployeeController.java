package br.com.nnast.payrollspringapi.controllers;

import br.com.nnast.payrollspringapi.assemblers.EmployeeModelAssembler;
import br.com.nnast.payrollspringapi.entities.Employee;
import br.com.nnast.payrollspringapi.entities.ResourceSupport;
import br.com.nnast.payrollspringapi.exceptions.EmployeeNotFoundException;
import br.com.nnast.payrollspringapi.repositories.EmployeeRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Root node
    @GetMapping("/")
    public RepresentationModel<?> rootResource() {
        RepresentationModel<?> rootResource = new RepresentationModel<>();
        rootResource.add(
                linkTo(methodOn(EmployeeController.class).rootResource()).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).findAll()).withRel("employees")
        );

        return rootResource;
    }

    @GetMapping("/employees")
    public ResponseEntity<CollectionModel<EntityModel<Employee>>> findAll() {
        return ResponseEntity.ok(
                assembler.toCollectionModel(this.repository.findAll())
        );
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EntityModel<Employee>> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(
                assembler.toModel(this.repository.findById(id)
                        .orElseThrow(() -> new EmployeeNotFoundException(id)))
        );
    }

    @PostMapping("/employees")
    public ResponseEntity<?> save(@RequestBody Employee employee) {
        EntityModel<Employee> modelEmp = assembler.toModel(repository.save(employee));

        return ResponseEntity.created(modelEmp.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(modelEmp);
    }

    @PutMapping("/employees" + "/{id}")
    public ResponseEntity<?> update(Employee newEmployee, Long id) {
        EntityModel<Employee> employee = repository.findById(id)
                .map(emp -> {
                    emp.setName(newEmployee.getName());
                    emp.setRole(newEmployee.getRole());
                    return assembler.toModel(repository.save(emp));
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return assembler.toModel(repository.save(newEmployee));
                });

        return ResponseEntity.created(employee.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(employee);

    }

    @DeleteMapping("/employees" + "/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
