package com.ghost.maremi01.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_category")
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String imgUrl;
    private Long id_market;

    @JsonIgnore
    @OneToMany(mappedBy = "categories")
    private List<Product> products = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "market_id")
    private Market market;

    public Category() {
    }

    public Category(Long id, String name,String description, String imgUrl, Market market, Long id_market) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.market = market;
        this.id_market = id_market;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //ID_MARKET


    public Long getId_market() {
        return id_market;
    }

    public void setId_market(Long id_market) {
        this.id_market = id_market;
    }

    //JOINS
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Market getMarket() {
        return market;
    }
//    public Long getMarketid() {
//
//        return market.getId();
//
//    }

    public void setMarket(Market market) {this.market = market; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id.equals(category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
