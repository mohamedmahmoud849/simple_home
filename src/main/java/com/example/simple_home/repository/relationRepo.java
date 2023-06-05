package com.example.simple_home.repository;

import com.example.simple_home.relation.relationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface relationRepo extends JpaRepository<relationEntity,Long> {
}
