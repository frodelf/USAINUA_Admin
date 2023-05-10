package com.avadamedia.USAINUA_Admin.controllers;

import com.avadamedia.USAINUA_Admin.enums.ContextPath;
import com.avadamedia.USAINUA_Admin.enums.Status;
import com.avadamedia.USAINUA_Admin.enums.Transport;
import com.avadamedia.USAINUA_Admin.enums.Type;
import com.avadamedia.USAINUA_Admin.entity.*;
import com.avadamedia.USAINUA_Admin.services.impl.*;
import com.avadamedia.USAINUA_Admin.util.ImageUtil;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class AdminController {
    private final ShopsServiceImpl shopsServiceImpl;
    private final ProductsServiceImpl productsServiceImpl;
    private final StorageServiceImpl storageServiceImpl;
    private final AdditionalServicesServiceImpl additionalServicesService;
    private final StatsServiceImpl statsServiceImpl;
    private final UsersServiceImpl usersServiceImpl;
    private final NewsServiceImpl newsService;
    private final OrdersServiceImpl ordersService;

    @ModelAttribute("header")
    public String getHeader(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", usersServiceImpl.getByEmail(authentication.getName()).getEmail());
        return "blocks/navbar";
    }
    @GetMapping("/admin/")
    public String stats(Model model){
        log.info("admin");
        model.addAttribute("woman", usersServiceImpl.getAllWoman().size());
        model.addAttribute("man", usersServiceImpl.getAllMan().size());
        model.addAttribute("months", new Gson().toJson(statsServiceImpl.getAllMonth()));
        model.addAttribute("value", statsServiceImpl.getAllValue());
        model.addAttribute("shops", shopsServiceImpl.getAll().size());
        model.addAttribute("products", productsServiceImpl.getAll().size());
        model.addAttribute("users", usersServiceImpl.getAll().size());
        return "admin/stats";
    }
    @GetMapping("/admin/shops/")
    public String shop(Model model){
        log.info("shops");
        model.addAttribute("shops", shopsServiceImpl.getAll());
        return "admin/shops";
    }
    @GetMapping("/admin/shop/add/")
    public String shopAddStart(){
        log.info("shop");
        return "admin/shops-add";
    }
    @PostMapping("/admin/shop/add/")
    public String shopAddEnd(@RequestParam("link")String link, @RequestParam("image")MultipartFile image) throws IOException {
        Shops shops = new Shops();
        shops.setLink(link);
        ImageUtil.imageForShop(shops, image);
        shopsServiceImpl.save(shops);
        return "redirect:/admin/shops/";
    }
    @PostMapping("/admin/shop/delete/{id}")
    public String shopDeleteById(@PathVariable("id")long id){
        shopsServiceImpl.deleteById(id);
        return "redirect:/admin/shops/";
    }
    @GetMapping("/admin/shop/edit/{id}")
    public String shopEditByIdStart(@PathVariable("id")long id, Model model){
        model.addAttribute("id", id);
        model.addAttribute("shop", shopsServiceImpl.getById(id));
        return "admin/shops-edit";
    }
    @PostMapping("/admin/shop/edit/{id}")
    public String shopEditByIdEnd(@PathVariable("id")long id, @RequestParam("link")String link, @RequestParam("image")MultipartFile image) throws IOException {
        Shops shops = shopsServiceImpl.getById(id);
        shops.setLink(link);
        if(!image.isEmpty()) ImageUtil.imageForShop(shops, image);
        shopsServiceImpl.save(shops);
        return "redirect:/admin/shops/";
    }
    @GetMapping("/admin/products/")
    public String products(Model model){
        log.info("products");
        model.addAttribute("products", productsServiceImpl.getAll());
        return "admin/products";
    }
    @GetMapping("/admin/product/add/")
    public String productAddStart(Model model){
        model.addAttribute("types", Type.values());
        return "admin/products-add";
    }
    @PostMapping("/admin/product/add/")
    public String productAddEnd(@RequestParam("name")String name, @RequestParam("price")String price, @RequestParam("link")String link,
                                @RequestParam("type")String type, @RequestParam("image")MultipartFile image)  {
        Products products = new Products();
        try{
        products.setName(name);
        products.setLink(link);
        products.setPrice(Double.parseDouble(price));
        products.setType(type);
        ImageUtil.imageForProducts(products, image);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productsServiceImpl.save(products);

        return "redirect:/admin/products/";
    }
    @PostMapping("/admin/product/delete/{id}")
    public String deleteById(@PathVariable("id")long id){
        productsServiceImpl.deleteById(id);
        return "redirect:/admin/products/";
    }
    @GetMapping("/admin/product/edit/{id}")
    public String productEditStart(@PathVariable("id")long id, Model model){
        model.addAttribute("types", Type.values());
        model.addAttribute("product", productsServiceImpl.getById(id));
        return "admin/products-edit";
    }
    @PostMapping("/admin/product/edit/{id}")
    public String productEditEnd(@PathVariable("id")long id, @RequestParam("name")String name, @RequestParam("price")String price,
                                 @RequestParam("link")String link, @RequestParam("type")String type,
                                 @RequestParam("image")MultipartFile image) throws IOException {
        Products products = productsServiceImpl.getById(id);
        products.setName(name);
        products.setPrice(Double.parseDouble(price));
        products.setLink(link);
        if(type != null)products.setType(type);
        if(!image.isEmpty()) ImageUtil.imageForProducts(products, image);
        productsServiceImpl.save(products);
        return "redirect:/admin/products/";
    }
    @GetMapping("/admin/additional-services/")
    public String additionalServices(Model model){
        model.addAttribute("services", additionalServicesService.getAll());
        return "admin/additional-services";
    }
    @GetMapping("/admin/additional-services/add/")
    public String additionalServicesStart(){
        return "admin/additional-services-add";
    }
    @PostMapping("/admin/additional-services/add/")
    public String additionalServicesEnd(@RequestParam("name")String name, @RequestParam("price")String price){
        AdditionalServices additionalServices = new AdditionalServices();
        additionalServices.setName(name);
        additionalServices.setPrice(Double.parseDouble(price));
        additionalServicesService.save(additionalServices);
        return "redirect:/admin/additional-services/";
    }
    @GetMapping("/admin/additional-services/edit/{id}")
    public String additionalServicesEditStart(@PathVariable("id")Long id, Model model){
        model.addAttribute("service", additionalServicesService.getById(id));
        return "admin/additional-services-edit";
    }
    @PostMapping("/admin/additional-services/edit/{id}")
    public String additionalServicesEditEnd(@PathVariable("id")Long id, @RequestParam("name")String name, @RequestParam("price")String price){
        AdditionalServices additionalServices = additionalServicesService.getById(id);
        additionalServices.setName(name);
        additionalServices.setPrice(Double.parseDouble(price));
        additionalServicesService.save(additionalServices);
        return "redirect:/admin/additional-services/";
    }
    @PostMapping("/admin/additional-services/delete/{id}")
    public String additionalServicesDeleteEnd(@PathVariable("id")Long id){
        additionalServicesService.deleteById(id);
        return "redirect:/admin/additional-services/";
    }
    @GetMapping("/admin/storages/")
    public String storage(Model model){
        model.addAttribute("storages", storageServiceImpl.getAll());
        return "admin/storages";
    }
    @GetMapping("/admin/storage/edit/{id}")
    public String storageEditStart(@PathVariable("id")Long id, Model model){
        model.addAttribute("storage", storageServiceImpl.getById(id));
        return "admin/storage-edit";
    }
    @PostMapping("/admin/storage/edit/{id}")
    public String storageAddEnd(@PathVariable("id")Long id,@RequestParam("name")String name, @RequestParam("street")String street,
                                @RequestParam("city")String city, @RequestParam("state")String state,
                                @RequestParam("index")String index, @RequestParam("phone")String phone){
        Storage storage = storageServiceImpl.getById(id);
        storage.setFullName(name);
        storage.setStreet(street);
        storage.setCity(city);
        storage.setState(state);
        storage.setIndex(index);
        storage.setPhone(phone);
        storageServiceImpl.save(storage);
        return "redirect:/admin/storages/";
    }
    @PostMapping("/admin/storage/delete/{id}")
    public String storage(@PathVariable("id")Long id){
        storageServiceImpl.deleteById(id);
        return "redirect:/admin/storages/";
    }
    @GetMapping("/admin/storage/add/")
    public String storageAddStart(){
        return "admin/storage-add";
    }
    @PostMapping("/admin/storage/add/")
    public String storageAddEnd(@RequestParam("name")String name, @RequestParam("street")String street,
                                @RequestParam("city")String city, @RequestParam("state")String state,
                                @RequestParam("index")String index, @RequestParam("phone")String phone){
        Storage storage = new Storage();
        storage.setFullName(name);
        storage.setStreet(street);
        storage.setCity(city);
        storage.setState(state);
        storage.setIndex(index);
        storage.setPhone(phone);
        storageServiceImpl.save(storage);
        return "redirect:/admin/storages/";
    }
    @GetMapping("/admin/news/")
    public String getNews(Model model){
        model.addAttribute("news", newsService.getAll());
        return "admin/news";
    }
    @GetMapping("/admin/news/add/")
    public String newsAddStart(){
        return "admin/news-add";
    }
    @PostMapping("/admin/news/add/")
    public String newsAddEnd(@RequestParam("name")String name, @RequestParam("description")String description){
        News news = new News();
        news.setText(description);
        news.setTitle(name);
        news.setDate(Date.valueOf(LocalDate.now()));
        newsService.save(news);
        return "redirect:/admin/news/";
    }
    @GetMapping("/admin/news/edit/{id}")
    public String newsEditStart(@PathVariable("id")Long id, Model model){
        model.addAttribute("news", newsService.getById(id));
        return "admin/news-edit";
    }
    @PostMapping("/admin/news/edit/{id}")
    public String newsEditEnd(@PathVariable("id")Long id, @RequestParam("name")String name, @RequestParam("description")String description){
        News news = newsService.getById(id);
        news.setText(description);
        news.setTitle(name);
        news.setDate(Date.valueOf(LocalDate.now()));
        newsService.save(news);
        return "redirect:/admin/news/";
    }
    @PostMapping("/admin/news/delete/{id}")
    public String newsDelete(@PathVariable("id")Long id){
        newsService.deleteById(id);
        return "redirect:/admin/news/";
    }
    @GetMapping("/admin/orders/")
    public String getOrders(Model model){
        model.addAttribute("orders", ordersService.getAll());
        return "admin/orders";
    }
    @GetMapping("/admin/orders/edit/{id}")
    public String orderEditStart(@PathVariable("id")Long id, Model model){
        model.addAttribute("order_type", ordersService.getById(id).isOnlyDelivery() ? "Тільки доставка" : "Покупка і доставка");
        model.addAttribute("additionalServices", additionalServicesService.getAll());
        model.addAttribute("order", ordersService.getById(id));
        model.addAttribute("transport", Transport.getAll());
        return "admin/orders-edit";
    }
    @PostMapping("/admin/orders/edit/{type}/{id}")
    public String approximatePrice(@PathVariable("type")boolean type, @PathVariable("id")long id, @RequestParam("transport")String transport,
                                 @RequestParam("weight")String weight, @RequestParam("price")String price,
                                 @RequestParam("services") List<Long> additionalServicesId){
        Orders orders = ordersService.getById(id);
        additionalServicesId.remove(0);
        List<AdditionalServices> additionalServices = new ArrayList<>();

        try {
            for (Long aLong : additionalServicesId) {
                additionalServices.add(additionalServicesService.getById(aLong));
            }
        }catch (Exception e){}

        double totalPrice = 0;
        if(type){
            try{
                for (AdditionalServices additionalService : additionalServices) {
                    totalPrice +=additionalService.getPrice();
                }
            }catch (Exception e){
            }
            if(transport.equals("plane")) totalPrice += 0.5*Double.valueOf(weight)+1000;
            else if(transport.equals("ship")) totalPrice += 0.3*Double.valueOf(weight)+500;
            else totalPrice += 800;
        }else {
            try{
                for (AdditionalServices additionalService : additionalServices) {
                    totalPrice+=additionalService.getPrice();
                }
            }catch (Exception e){
            }
            if(transport.equals("plane"))totalPrice += 0.1*Double.valueOf(price)+0.5*Double.valueOf(weight)+1000;
            else if(transport.equals("ship"))totalPrice += 0.05*Double.valueOf(price)+0.3*Double.valueOf(weight)+500;
            else totalPrice += 800;
        }
        orders.setStatus(Status.READY_FOR_PAYMENT.getStatus());
        orders.setTotalPrice(totalPrice);
        ordersService.save(orders);
        return "redirect:/admin/orders/edit/"+id;
    }

    @GetMapping("/admin/test/")
    public String test(){
        return "admin/test";
    }
}
