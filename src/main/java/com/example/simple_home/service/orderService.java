package com.example.simple_home.service;

import com.example.simple_home.model.CardItem;
import com.example.simple_home.model.Order;
import com.example.simple_home.model.Product;
import com.example.simple_home.repository.orderRepo;
import com.example.simple_home.repository.projection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor
public class orderService {
    private final orderRepo orderRepo;
    private final productService productService;

    public Order insertNewOrder(List<CardItem> list){
        Long totalQuantity=0L,totalPrice=0L;

        for (CardItem cardItem:
             list) {
            totalQuantity += cardItem.getQuantity();
            totalPrice += Long.valueOf(cardItem.getPrice()) * cardItem.getQuantity();
        }
       return orderRepo.save(Order.builder()
                        .orderDate(new Date())
                        .itemsQuantity(totalQuantity)
                        .totalPrice(totalPrice)
                        .build());

    }

    public List<CardItem> deleteCardItem(List<CardItem> list,String name){
        list.removeIf(x->x.getName().equals(name));
        return list;
    }

    public List<CardItem> addNewCardItem( List<CardItem> cardItems,Long id , Long quantity){
        if(cardItems.stream().anyMatch(x->x.getName().equals(productService.getProductById(id).getName()))){
            CardItem cardItem=cardItems.stream().filter(x->x.getName().equals(productService.getProductById(id).getName())).findFirst().get();
            cardItem.setQuantity(cardItem.getQuantity()+quantity);
            cardItems.removeIf(x->x.getName().equals(cardItem.getName()));
            cardItems.add(cardItem);
            return cardItems;
        }else{
            cardItems.add(CardItem.builder()
                    .name(productService.getProductById(id).getName())
                    .description(productService.getProductById(id).getDescription())
                    .price(productService.getProductById(id).getPrice())
                    .image(productService.getProductById(id).getImage())
                    .quantity(quantity)
                    .build());
            return cardItems;
        }

    }

    public Order getOrderDetails(Long id){
        return orderRepo.findById(id).get();
    }
    public List<projection> getProjection(Long id){
        return orderRepo.getProjection(id);
    }

    public List<Product> getProductsForOrderDetails(List<projection> list){
        return list.stream().flatMapToLong(x-> LongStream.of(x.getItemId())).mapToObj(productService::getProductById).toList();

    }
    public List<CardItem> getCardItemsForOrderDetails(List<projection> list){
        List<CardItem> newCardItemsList = new ArrayList<>();
        List<Product> products = getProductsForOrderDetails(list);
        products.stream().map(Product::getImage).toList();
        for(int i=0;i< list.size();i++){
            newCardItemsList.add(CardItem.builder()
                    .name(products.get(i).getName())
                    .description(products.get(i).getDescription())
                    .price(products.get(i).getPrice())
                    .image(products.get(i).getImage())
                    .quantity(list.get(i).getQuantity())
                    .build());
        }
        return newCardItemsList;


    }
    public List<Order> getAllOrders(){
        return orderRepo.findAll();
    }

}
