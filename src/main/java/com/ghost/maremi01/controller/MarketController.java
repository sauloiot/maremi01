package com.ghost.maremi01.controller;

import com.ghost.maremi01.exception.PersonalNotFoundException;
import com.ghost.maremi01.model.Market;
import com.ghost.maremi01.repository.MarketRepository;
import com.ghost.maremi01.repository.ProductRepository;
import com.ghost.maremi01.resource.MarketResourceAssembler;
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
@RequestMapping( value = "/markets")
public class MarketController {

    @Autowired
    private final MarketRepository repository;
    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final MarketResourceAssembler assembler;

    public MarketController(MarketRepository repository, MarketResourceAssembler assembler, ProductRepository productRepository) {
        this.repository = repository;
        this.assembler = assembler;
        this.productRepository = productRepository;
    }

    //GET all
    @GetMapping
    public CollectionModel<EntityModel<Market>> getAllMarkets(){
        System.out.println("HTTP GET: ALL MARKETS");

        List<EntityModel<Market>> products = repository
                .findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(products,
                linkTo(methodOn(MarketController.class).getAllMarkets()).withSelfRel());

    }

    //GET by id
    @GetMapping("/{id}")
    public EntityModel getById(@PathVariable Long id){
        System.out.println("HTTP GET: BY ID, market: "+ repository.findById(id));

        Market market = repository.findById(id)
                .orElseThrow(()-> new PersonalNotFoundException(id));

        return assembler.toModel(market);
    }

    //POST
    @PostMapping
    public ResponseEntity<?> postNewProduct(@RequestBody Market newMarket) throws URISyntaxException {

        EntityModel<Market> market = assembler.toModel(repository.save(newMarket));
        System.out.println("HTTP POST: MARKET:" + newMarket + " created");

        return ResponseEntity
                .created(new URI(market.getLink("markets").get().getHref()))
                .body(market);
    }


    //PUT by id
    @PutMapping("/{id}")
    public ResponseEntity<?> putProductById(@RequestBody Market newMarket,@PathVariable Long id) throws URISyntaxException {
        System.out.println("HTTP PUT: MARKET:" + newMarket + " updated");
        Market updateMarket = repository.findById(id)
                .map(market -> {
                    market.setName(newMarket.getName());
                    market.setNumCnpj(newMarket.getNumCnpj());
                    market.setPhone(newMarket.getPhone());
                    market.setAdress(newMarket.getAdress());
                    market.setImgUrl(newMarket.getImgUrl());

                    return repository.save(market);
                })
                .orElseGet(()->{
                    newMarket.setId(id);
                    return repository.save(newMarket);
                });

        EntityModel<Market> resource = assembler.toModel(updateMarket);

        return ResponseEntity
                .created(new URI(resource.getLink("markets").get().getHref()))
                .body(resource);

    }


    //DELETE by id
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteMarketById(@PathVariable Long id){
        System.out.println("HTTP DELETE: " + repository.findById(id)+ " deleted");
        //IMPLEMENTAR A DELÇÃO DO PRODUCT E CATEGORY

       // productRepository.deleteAll();
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
