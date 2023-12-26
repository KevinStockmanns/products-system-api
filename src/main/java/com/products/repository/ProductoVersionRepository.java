package com.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.products.entity.ProductoVersion;

public interface ProductoVersionRepository extends JpaRepository<ProductoVersion, Long>{
    
}
