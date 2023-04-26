package br.com.nnast.payrollspringapi.assemblers;

import br.com.nnast.payrollspringapi.controllers.OrderController;
import br.com.nnast.payrollspringapi.entities.Order;
import br.com.nnast.payrollspringapi.entities.Status;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

    @Override
    public EntityModel<Order> toModel(Order entity) {
        EntityModel<Order> orders = EntityModel.of(entity,
                linkTo(methodOn(OrderController.class).findOne(entity.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).findAll()).withRel("orders"));


        if (entity.getStatus() == Status.IN_PROGRESS) {
            orders.add(linkTo(methodOn(OrderController.class).cancel(entity.getId())).withSelfRel(),
                    linkTo(methodOn(OrderController.class).complete(entity.getId())).withSelfRel());
        }
        return orders;
    }
}
