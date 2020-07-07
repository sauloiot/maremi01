package com.ghost.maremi01.resource;

import com.ghost.maremi01.controller.ProductController;
import com.ghost.maremi01.model.Product;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ProductResourceAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {
    public ProductResourceAssembler() {
    }

    @Override
    public EntityModel<Product> toModel(Product product) {

        return new EntityModel<>(product,
                linkTo(methodOn(ProductController.class).getById(product.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));
    }
}
