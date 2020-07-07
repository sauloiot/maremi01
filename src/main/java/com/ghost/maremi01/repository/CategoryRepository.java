package com.ghost.maremi01.repository;

import com.ghost.maremi01.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
