package com.example.simple_home.repository;

import com.example.simple_home.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface customerRepo extends JpaRepository<Customer,Long> {
}
