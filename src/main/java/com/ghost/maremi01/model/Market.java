package com.ghost.maremi01.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "tb_market")
public class Market implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigInteger numCnpj;
    private String phone;
    private String adress;
    private String imgUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "market")
    private List<Product> products = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "market")
    private List<Category> categories = new ArrayList<>();



    public Market() {
    }

    public Market(Long id, String name, BigInteger cnpj, String phone, String adress, String imgUrl) {
        super();
        this.id = id;
        this.name = name;
        this.numCnpj = cnpj;
        this.phone = phone;
        this.adress = adress;
        this.imgUrl = imgUrl;

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

    public BigInteger getNumCnpj() {
        return numCnpj;
    }

    public void setNumCnpj(BigInteger cnpj) {
        this.numCnpj = cnpj;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Market market = (Market) o;
        return id.equals(market.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Market{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cnpj=" + numCnpj +
                ", phone='" + phone + '\'' +
                ", adress='" + adress + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
