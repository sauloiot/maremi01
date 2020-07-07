package com.ghost.maremi01.controller;

import com.ghost.maremi01.exception.PersonalNotFoundException;
import com.ghost.maremi01.model.Product;
import com.ghost.maremi01.repository.ProductRepository;
import com.ghost.maremi01.resource.ProductResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.*;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final ProductResourceAssembler productResourceAssembler;

    public ProductController(ProductRepository productRepository, ProductResourceAssembler productResourceAssembler) {
        this.productRepository = productRepository;
        this.productResourceAssembler = productResourceAssembler;
    }

    //GET all
    @GetMapping
    public CollectionModel<EntityModel<Product>> getAllProducts(){
        System.out.println("HTTP GET: ALL PRODUCTS");

        List<EntityModel<Product>> products = productRepository
                .findAll()
                .stream()
                .map(productResourceAssembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(products,
                linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());

    }

    //GET by id
    @GetMapping("/{id}")
    public EntityModel getById(@PathVariable Long id){
        System.out.println("HTTP GET: BY ID, product: "+ productRepository.findById(id));

        Product product = productRepository.findById(id)
                .orElseThrow(()-> new PersonalNotFoundException(id));

        return productResourceAssembler.toModel(product);
    }

    //POST
    @PostMapping
    public ResponseEntity<?> postNewProduct(@RequestBody Product newProduct) throws URISyntaxException {

        EntityModel<Product> product = productResourceAssembler.toModel(productRepository.save(newProduct));
        System.out.println("HTTP POST: PRODUCT:" + newProduct + " created");

        return ResponseEntity
                .created(new URI(product.getLink("products").get().getHref()))
                .body(product);
    }

    //PUT by id
    @PutMapping("/{id}")
    public ResponseEntity<?> putProductById(@RequestBody Product newProduct,@PathVariable Long id) throws URISyntaxException {
        System.out.println("HTTP PUT: PRODUCT:" + newProduct + " updated");
        Product updateProduct = productRepository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setPrice(newProduct.getPrice());
                    product.setDescription(newProduct.getDescription());
                    product.setImgUrl(newProduct.getImgUrl());

                    return productRepository.save(product);
                })
                .orElseGet(()->{
                    newProduct.setId(id);
                    return productRepository.save(newProduct);
                });

                EntityModel<Product> resource = productResourceAssembler.toModel(updateProduct);

                return ResponseEntity
                        .created(new URI(resource.getLink("products").get().getHref()))
                        .body(resource);

    }

    //DELETE by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id){
        System.out.println("HTTP DELETE: " + productRepository.findById(id)+ " deleted");

        productRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
