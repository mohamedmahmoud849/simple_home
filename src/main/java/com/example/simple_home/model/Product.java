package com.example.simple_home.model;

import com.example.simple_home.relation.relationEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String price;
    private String category;
    @Lob
    @Column(columnDefinition = "bigint")
    private String image;
    @OneToMany(mappedBy = "product")
    private List<relationEntity> relations;

}
