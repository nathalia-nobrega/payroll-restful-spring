package br.com.nnast.payrollspringapi.controller;

import br.com.nnast.payrollspringapi.assemblers.EmployeeModelAssembler;
import br.com.nnast.payrollspringapi.controllers.EmployeeController;
import br.com.nnast.payrollspringapi.entities.Employee;
import br.com.nnast.payrollspringapi.repositories.EmployeeRepository;
import jdk.jfr.Name;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
@Import(EmployeeModelAssembler.class)
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private EmployeeRepository repository;

    @Test
    public void getShouldFetchHalDocument() throws Exception {
        given(repository.findAll()).willReturn(
                Arrays.asList(
                        new Employee("Frodo", "Baggins", "ring bearer"),
                        new Employee("Bilbo", "Baggins", "burglar")));

        mvc.perform(get("/employees/1").accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.name", is("Bilbo Baggins")));
    }
}
