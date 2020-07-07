package com.ghost.maremi01.resource;

import com.ghost.maremi01.controller.MarketController;
import com.ghost.maremi01.controller.ProductController;
import com.ghost.maremi01.model.Market;
import com.ghost.maremi01.model.Product;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.methodOn;

@Component
public class MarketResourceAssembler implements RepresentationModelAssembler<Market, EntityModel<Market>> {
    public MarketResourceAssembler() {
    }

    @Override
    public EntityModel<Market> toModel(Market market) {

        return new EntityModel<>(market,
                linkTo(methodOn(MarketController.class).getById(market.getId())).withSelfRel(),
                linkTo(methodOn(MarketController.class).getAllMarkets()).withRel("markets"));
    }
}
