package com.example.simple_home.repository;

import com.example.simple_home.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface orderRepo extends JpaRepository<Order,Long> {
    @Query(value = "select r.product_id as itemId ,i.name as name ,i.image as image ,i.description as description ,i.price as price ,r.quantity as quantity\n" +
            "from public.relations as r , public.products as i\n" +
            "where r.order_id=:id and i.id=r.product_id ",nativeQuery = true)
    List<projection> getProjection(@Param("id") Long id);

}
