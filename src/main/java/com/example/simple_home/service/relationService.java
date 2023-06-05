package com.example.simple_home.service;

import com.example.simple_home.model.CardItem;
import com.example.simple_home.relation.compositeKey;
import com.example.simple_home.relation.relationEntity;
import com.example.simple_home.repository.orderRepo;
import com.example.simple_home.repository.productRepo;
import com.example.simple_home.repository.relationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class relationService {
    private final relationRepo relationRepo;
    private final orderRepo orderRepo;
    private final productRepo productRepo;

    public void creatRelations(List<CardItem> list,Long order_id){
        List<relationEntity> relations=new ArrayList<>();
        for (CardItem cardItem:
             list) {
            relations.add(relationEntity.builder()
                            .id(compositeKey.builder().orderId(order_id).productId(productRepo.findByName(cardItem.getName()).get().getId()).build())
                            .quantity(cardItem.getQuantity())
                            .order(orderRepo.findById(order_id).get())
                            .product(productRepo.findByName(cardItem.getName()).get())
                            .build()
            );
        }
        relationRepo.saveAll(relations);

    }
}
