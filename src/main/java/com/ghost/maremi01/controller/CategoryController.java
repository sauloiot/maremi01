package com.ghost.maremi01.controller;

import com.ghost.maremi01.repository.CategoryRepository;
import com.ghost.maremi01.repository.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    //GET all

    //GET by id

    //POST

    //PUT by id

    //DELETE by id
}
