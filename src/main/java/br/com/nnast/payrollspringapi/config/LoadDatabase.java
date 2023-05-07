package br.com.nnast.payrollspringapi.config;

import br.com.nnast.payrollspringapi.entities.Employee;
import br.com.nnast.payrollspringapi.entities.Order;
import br.com.nnast.payrollspringapi.entities.Status;
import br.com.nnast.payrollspringapi.repositories.EmployeeRepository;
import br.com.nnast.payrollspringapi.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
class LoadDatabase {

    private static final Logger LOG = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
        return args -> {
            employeeRepository.save(new Employee("Bilbo", "Baggins", "burglar"));
            employeeRepository.save(new Employee("Frodo", "Baggins", "thief"));
            employeeRepository.findAll().forEach(employee -> LOG.info("Preloaded " + employee));

            /*orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
            orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));
            orderRepository.findAll().forEach(order -> LOG.info("Preloaded: " + order));*/
        };
    }
}