package com.example.simple_home.repository;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Lob;
import lombok.Builder;
import org.hibernate.engine.jdbc.LobCreationContext;

public interface projection {
    Long getItemId();
    String getName();
    String getDescription();
    String getPrice();
    Long getQuantity();

    String getImage();



}
