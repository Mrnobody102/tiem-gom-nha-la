package com.example.tiemgomnhala.repository;

import com.example.tiemgomnhala.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}
