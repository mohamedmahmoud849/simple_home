package com.example.simple_home.controller;

import com.example.simple_home.model.CardItem;
import com.example.simple_home.model.Order;
import com.example.simple_home.model.Product;
import com.example.simple_home.service.orderService;
import com.example.simple_home.service.productService;
import com.example.simple_home.service.relationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

@Log4j2
@Controller
@RequiredArgsConstructor
public class homeControl {
    private final productService productService;
    private final relationService relationService;
    private final orderService orderService;
    private final HttpSession httpSession;
    private List<CardItem> cardItems = new ArrayList<>();
    @GetMapping("/")
    public ModelAndView pizzaPage(Model model){
        model.addAttribute("card_size",cardItems.size());
        return new ModelAndView("index","products",productService.getALlByCategory("Pizza"));
    }
    @GetMapping("/salad")
    public ModelAndView saladPage(Model model){
        model.addAttribute("card_size",cardItems.size());
        return new ModelAndView("index","products",productService.getALlByCategory("Salad"));
    }
    @GetMapping("/noodle")
    public ModelAndView noodlePage(Model model){
        model.addAttribute("card_size",cardItems.size());
        return new ModelAndView("index","products",productService.getALlByCategory("Noodle"));
    }
    @RequestMapping("/item/{id}")
    public String itemPage(@PathVariable Long id, Model model){
        model.addAttribute("item",productService.getProductById(id));
        httpSession.setAttribute("product", productService.getProductById(id));
        httpSession.setAttribute("ses_text", String.valueOf("lol"));
        model.addAttribute("session_obj",httpSession.getAttribute("product"));
        model.addAttribute("session_text",httpSession.getAttribute("ses_text"));
        return "item_page";
    }
    @GetMapping("/add")
    public String about(){
        return "new_product";
    }
    @PostMapping("/add_product")
    public String addNewProduct(@RequestParam("file") MultipartFile file,
                                @RequestParam("name") String name,
                                @RequestParam("description") String description,
                                @RequestParam("category") String category,
                                @RequestParam("price") String price){
        productService.SaveImgDb(file, description,name , price,category);
        return "redirect:/";
    }
    @RequestMapping("/card/{id}")
    public String card(@PathVariable Long id,@RequestParam("quantity") Long quantity,Model model){
        httpSession.setAttribute("card_items_list",cardItems);
        httpSession.setAttribute("total_price",productService.calculateTotalPrice(cardItems));
        List<CardItem> list =(List<CardItem>) httpSession.getAttribute("card_items_list");
        model.addAttribute("list",orderService.addNewCardItem(list,id,quantity));
        return "redirect:/";
    }
    @RequestMapping("/card")
    public String cardPage(Model model){
        model.addAttribute("items",httpSession.getAttribute("card_items_list"));
        model.addAttribute("total_price",httpSession.getAttribute("total_price"));
        return "card_page";
    }
    @RequestMapping("/payment")
    public String payment(){
        //making order and set relations to it
        List<CardItem> list =(List<CardItem>) httpSession.getAttribute("card_items_list");
        Order o = orderService.insertNewOrder(list);
        relationService.creatRelations(list,o.getId());
        return "order_details";
    }
    @GetMapping("/delete_card_item/{name}")
    public String deleteCardItem(@PathVariable String name){
        List<CardItem> list =(List<CardItem>) httpSession.getAttribute("card_items_list");
        httpSession.setAttribute("card_items_list",orderService.deleteCardItem(list,name));
        return "redirect:/card";
    }
    @RequestMapping("/order/{id}")
    public String getOrderDetails(@PathVariable Long id, Model model){
        model.addAttribute("order",orderService.getOrderDetails(id));
        model.addAttribute("order_items",orderService.getCardItemsForOrderDetails(orderService.getProjection(id)));
        return "order_details";
    }
    @RequestMapping("/Customer_order")
    public String getCustomerOrders(Model model){
        model.addAttribute("orders",orderService.getAllOrders());
        return "customer_order";
    }


}
