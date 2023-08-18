package com.example.productservice.repository;

import com.example.productservice.entity.ProductLookup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductLookupRepository extends JpaRepository<ProductLookup, String> {
    List<ProductLookup> findByProductIdOrTitle(String productId, String title);
}