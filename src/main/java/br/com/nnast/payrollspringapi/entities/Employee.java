package br.com.nnast.payrollspringapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Entity
@Table(name = "EMPLOYEES")
@NoArgsConstructor
@JsonIgnoreProperties (ignoreUnknown = true)
public class Employee {
    private @Id
    @GeneratedValue Long id;
    private String fName;
    private String lName;
    private String role;

    public Employee(String fName, String lName, String role) {
        this.fName = fName;
        this.lName = lName;
        this.role = role;
    }

    public String getName() {
        return this.fName + " " + this.lName;
    }

    public void setName(String name) {
        String[] parts = name.split(" ");
        this.fName = parts[0];
        this.lName = parts[1];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
