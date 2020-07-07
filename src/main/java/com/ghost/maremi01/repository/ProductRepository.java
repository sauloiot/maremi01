package com.ghost.maremi01.repository;

import com.ghost.maremi01.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
