package com.example.simple_home.config;

import com.example.simple_home.model.Customer;
import com.example.simple_home.model.Order;
import com.example.simple_home.repository.customerRepo;
import com.example.simple_home.repository.orderRepo;
import com.example.simple_home.service.orderService;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.LobCreator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class config implements CommandLineRunner {
    private final orderService orderService;
    private final orderRepo orderRepo;
    private final customerRepo customerRepo;

    @Override
    public void run(String... args) throws Exception {
        orderRepo.save(Order.builder()
                        .customer(customerRepo.save(Customer.builder()
                                .firstName("ahmed")
                                .lastName("alaa")
                                .contactNumber("01258761122")
                                .email("ahmed@gmail.com")
                                .password("ahmed123")
                                .build()))
                        .orderDate(new Date())
                        .totalPrice(500L)
                        .itemsQuantity(5L)
                .build());


    }
}
