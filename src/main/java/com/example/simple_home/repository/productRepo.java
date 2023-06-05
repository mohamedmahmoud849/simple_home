package com.example.simple_home.repository;

import com.example.simple_home.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface productRepo extends JpaRepository<Product,Long> {
    @Transactional
    List<Product> findAllByCategory(String category);
    @Transactional
    Optional<Product> findByName(String name);
}
