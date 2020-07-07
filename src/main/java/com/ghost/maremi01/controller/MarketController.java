package com.ghost.maremi01.controller;

import com.ghost.maremi01.repository.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( value = "/markets")
public class MarketController {

    @Autowired
    private final MarketRepository marketRepository;

    public MarketController(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }

    //GET all

    //GET by id

    //POST

    //PUT by id

    //DELETE by id
}
