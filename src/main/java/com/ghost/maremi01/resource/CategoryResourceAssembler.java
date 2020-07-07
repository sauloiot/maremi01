package com.ghost.maremi01.resource;

import com.ghost.maremi01.controller.CategoryController;
import com.ghost.maremi01.controller.ProductController;
import com.ghost.maremi01.model.Category;
import com.ghost.maremi01.model.Product;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CategoryResourceAssembler implements RepresentationModelAssembler<Category, EntityModel<Category>> {
    public CategoryResourceAssembler() {
    }

    @Override
    public EntityModel<Category> toModel(Category category) {

        return new EntityModel<>(category,
                linkTo(methodOn(CategoryController.class).getById(category.getId())).withSelfRel(),
                linkTo(methodOn(CategoryController.class).getAllCategories()).withRel("categories"));
    }
}
