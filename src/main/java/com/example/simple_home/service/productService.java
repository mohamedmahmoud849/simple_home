package com.example.simple_home.service;

import com.example.simple_home.model.CardItem;
import com.example.simple_home.model.Product;
import com.example.simple_home.repository.productRepo;
import com.example.simple_home.repository.projection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.LongStream;
import java.util.zip.GZIPInputStream;

@Service
@RequiredArgsConstructor
public class productService {
    private final productRepo productRepo;

    public Product getProductById(Long id){
        return productRepo.findById(id).get();
    }
    public List<Product> getALlByCategory(String category){
        return productRepo.findAllByCategory(category);
    }
    public List<Product> getALl(){
        return productRepo.findAll();
    }

    public void SaveImgDb(MultipartFile file , String description , String name, String price,String category){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains("..")){
            System.out.println("not a prober file name");
        }else{

            try {
                productRepo.save(Product.builder()
                        .image(Base64.getEncoder().encodeToString(file.getBytes()))
                        .description(description)
                        .name(name)
                        .price(price)
                        .category(category)
                        .build());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }


    public String calculateTotalPrice(List<CardItem> itemsList){
        Long totalPrice = 0L;
        if(itemsList.isEmpty()){
            return String.valueOf(totalPrice);

        }else{
            for (CardItem cardItem:
                    itemsList) {
                totalPrice += Long.valueOf(cardItem.getPrice())* cardItem.getQuantity();
            }
            return String.valueOf(totalPrice);
        }


    }


}
