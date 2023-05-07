package br.com.nnast.payrollspringapi.controller;

import br.com.nnast.payrollspringapi.assemblers.OrderModelAssembler;
import br.com.nnast.payrollspringapi.controllers.OrderController;
import br.com.nnast.payrollspringapi.entities.Order;
import br.com.nnast.payrollspringapi.entities.Status;
import br.com.nnast.payrollspringapi.repositories.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.Name;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
@Import(OrderModelAssembler.class)
public class OrderControllerTests {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private OrderRepository repository;

    @Test
    @Name("ShouldInsertOrder")
    public void getShouldRetrieveOrder() throws Exception {
       mvc.perform(get("/orders/1").accept(MediaTypes.HAL_JSON_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.description", is("MacBook Pro")))
               .andReturn();
    }

}
