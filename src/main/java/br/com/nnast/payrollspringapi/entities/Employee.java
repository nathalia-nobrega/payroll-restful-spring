package br.com.nnast.payrollspringapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
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

    public void setName(String name) {
        String[] parts = name.split(" ");
        this.fName = parts[0];
        this.lName = parts[1];
    }
}
