package com.example.simple_home.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardItem {
    private String name;
    private String description;
    private String price;
    private String image;
    private Long quantity;
}
