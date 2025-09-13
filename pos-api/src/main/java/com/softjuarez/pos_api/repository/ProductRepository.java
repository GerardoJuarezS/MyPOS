package com.softjuarez.pos_api.repository;

import com.softjuarez.pos_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}