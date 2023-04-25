package br.com.nnast.payrollspringapi.config;

import br.com.nnast.payrollspringapi.entities.Employee;
import br.com.nnast.payrollspringapi.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
class LoadDatabase {

    private static final Logger LOG = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {
        return args -> {
            LOG.info("Preloading "+ repository.save(new Employee("Bilbo Baggins", "burglar")));
            LOG.info("Preloading "+ repository.save(new Employee("Frodo Baggins", "thief")));
        };
    }
}
