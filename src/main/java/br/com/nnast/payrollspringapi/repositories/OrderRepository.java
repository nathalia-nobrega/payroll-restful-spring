package br.com.nnast.payrollspringapi.repositories;

import br.com.nnast.payrollspringapi.entities.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
