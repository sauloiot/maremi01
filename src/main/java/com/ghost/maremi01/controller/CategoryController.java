package com.ghost.maremi01.controller;

import com.ghost.maremi01.exception.PersonalNotFoundException;
import com.ghost.maremi01.model.Category;
import com.ghost.maremi01.repository.CategoryRepository;
import com.ghost.maremi01.resource.CategoryResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private final CategoryRepository repository;

    @Autowired
    private final CategoryResourceAssembler assembler;

    public CategoryController(CategoryRepository repository, CategoryResourceAssembler assembler) {
        this.repository = repository;
        this. assembler = assembler;
    }

    //GET all
    @GetMapping
    public CollectionModel<EntityModel<Category>> getAllCategories(){
        System.out.println("HTTP GET: ALL PRODUCTS");

        List<EntityModel<Category>> category = repository
                .findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(category,
                linkTo(methodOn(CategoryController.class).getAllCategories()).withSelfRel());

    }

    //GET by id
    @GetMapping("/{id}")
    public EntityModel getById(@PathVariable Long id){
        System.out.println("HTTP GET: BY ID, product: "+ repository.findById(id));

        Category category = repository.findById(id)
                .orElseThrow(()-> new PersonalNotFoundException(id));

        return assembler.toModel(category);
    }


    //POST
    @PostMapping
    public ResponseEntity<?> postNewProduct(@RequestBody Category newCategory) throws URISyntaxException {

        EntityModel<Category> category = assembler.toModel(repository.save(newCategory));
        System.out.println("HTTP POST: PRODUCT:" + newCategory + " created");

        return ResponseEntity
                .created(new URI(category.getLink("categories").get().getHref()))
                .body(category);
    }

    //PUT by id
    @PutMapping("/{id}")
    public ResponseEntity<?> putCategoryById(@RequestBody Category newCategory,@PathVariable Long id) throws URISyntaxException {
        System.out.println("HTTP PUT: PRODUCT:" + newCategory + " updated");
        Category updateCategory = repository.findById(id)
                .map(product -> {
                    product.setName(newCategory.getName());
                    product.setDescription(newCategory.getDescription());
                    product.setImgUrl(newCategory.getImgUrl());

                    return repository.save(product);
                })
                .orElseGet(()->{
                    newCategory.setId(id);
                    return repository.save(newCategory);
                });

        EntityModel<Category> resource = assembler.toModel(updateCategory);

        return ResponseEntity
                .created(new URI(resource.getLink("categories").get().getHref()))
                .body(resource);

    }

    //DELETE by id
}
