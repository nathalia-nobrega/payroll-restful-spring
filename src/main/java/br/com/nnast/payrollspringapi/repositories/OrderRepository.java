package br.com.nnast.payrollspringapi.repositories;

import br.com.nnast.payrollspringapi.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
