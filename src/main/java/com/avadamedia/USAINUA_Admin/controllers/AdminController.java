package com.avadamedia.USAINUA_Admin.controllers;

import com.avadamedia.USAINUA_Admin.enums.Status;
import com.avadamedia.USAINUA_Admin.enums.Transport;
import com.avadamedia.USAINUA_Admin.enums.Type;
import com.avadamedia.USAINUA_Admin.entity.*;
import com.avadamedia.USAINUA_Admin.repositories.AdditionalServicesRepository;
import com.avadamedia.USAINUA_Admin.repositories.OrdersRepository;
import com.avadamedia.USAINUA_Admin.repositories.ProductsRepository;
import com.avadamedia.USAINUA_Admin.services.impl.*;
import com.avadamedia.USAINUA_Admin.util.ImageUtil;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final OrdersRepository ordersRepository;
    private final ProductsRepository productsRepository;
    private final ShopsServiceImpl shopsServiceImpl;
    private final ProductsServiceImpl productsServiceImpl;
    private final StorageServiceImpl storageServiceImpl;
    private final AdditionalServicesServiceImpl additionalServicesService;
    private final AdditionalServicesRepository additionalServicesRepository;
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
        Shop shop = new Shop();
        shop.setLink(link);
        shop.setImageName(ImageUtil.imageForShop(shop, image));
        shopsServiceImpl.save(shop);
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
        Shop shop = shopsServiceImpl.getById(id);
        shop.setLink(link);
        if(!image.isEmpty()) shop.setImageName(ImageUtil.imageForShop(shop, image));
        shopsServiceImpl.save(shop);
        return "redirect:/admin/shops/";
    }
    @GetMapping("/admin/products/{number}")
    public String products(Model model, @PathVariable("number")int number){
        model.addAttribute("current", number);
        if (number < 1) {
            return "redirect:/admin/products/1";
        }
        int max = (int) Math.ceil(productsServiceImpl.getAll().size() / 3.0);
        max = max==0?1:max;
        if (number > max) {
            return "redirect:/admin/products/"+max;
        }
        if(!productsServiceImpl.getAll().isEmpty()) {
            Page<Product> products = productsRepository.findAll(PageRequest.of((number - 1), 3));
            model.addAttribute("products", products);
            return "admin/products";
        }
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
        Product product = new Product();
        try{
        product.setName(name);
        product.setLink(link);
        product.setPrice(Double.parseDouble(price));
        product.setType(type);
        product.setImageName(ImageUtil.imageForProducts(product, image));
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productsServiceImpl.save(product);

        return "redirect:/admin/products/1";
    }
    @PostMapping("/admin/product/delete/{id}")
    public String deleteById(@PathVariable("id")long id){
        productsServiceImpl.deleteById(id);
        return "redirect:/admin/products/1";
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
        Product product = productsServiceImpl.getById(id);
        product.setName(name);
        product.setPrice(Double.parseDouble(price));
        product.setLink(link);
        if(type != null) product.setType(type);
        if(!image.isEmpty()) product.setImageName(ImageUtil.imageForProducts(product, image));
        productsServiceImpl.save(product);
        return "redirect:/admin/products/1";
    }
    @GetMapping("/admin/additional-services/{number}")
    public String additionalServices(Model model,@PathVariable("number")int number){
        model.addAttribute("current", number);
        if (number < 1) {
            return "redirect:/admin/additional-services/1";
        }
        int max = (int) Math.ceil(additionalServicesService.getAll().size() / 2.0);
        if (number > max  &&  max >1) {
            return "redirect:/admin/additional-services/"+(max);
        }
        if(!additionalServicesService.getAll().isEmpty()){
            Page<AdditionalService> additionalServices = additionalServicesRepository.findAll(PageRequest.of((number-1), 2));
            model.addAttribute("services", additionalServices);
            return "admin/additional-services";
        }
        model.addAttribute("services", additionalServicesService.getAll());
        return "admin/additional-services";
    }
    @GetMapping("/admin/additional-services/add/")
    public String additionalServicesStart(){
        return "admin/additional-services-add";
    }
    @PostMapping("/admin/additional-services/add/")
    public String additionalServicesEnd(@RequestParam("name")String name, @RequestParam("price")String price){
        AdditionalService additionalService = new AdditionalService();
        additionalService.setName(name);
        additionalService.setPrice(Double.parseDouble(price));
        additionalServicesService.save(additionalService);
        return "redirect:/admin/additional-services/0";
    }
    @GetMapping("/admin/additional-services/edit/{id}")
    public String additionalServicesEditStart(@PathVariable("id")Long id, Model model){
        model.addAttribute("service", additionalServicesService.getById(id));
        return "admin/additional-services-edit";
    }
    @PostMapping("/admin/additional-services/edit/{id}")
    public String additionalServicesEditEnd(@PathVariable("id")Long id, @RequestParam("name")String name, @RequestParam("price")String price){
        AdditionalService additionalService = additionalServicesService.getById(id);
        additionalService.setName(name);
        additionalService.setPrice(Double.parseDouble(price));
        additionalServicesService.save(additionalService);
        return "redirect:/admin/additional-services/0";
    }
    @PostMapping("/admin/additional-services/delete/{id}")
    public String additionalServicesDeleteEnd(@PathVariable("id")Long id){
        additionalServicesService.deleteById(id);
        return "redirect:/admin/additional-services/0";
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
    @GetMapping("/admin/orders/{number}")
    public String getOrders(Model model, @PathVariable("number")int number){
        model.addAttribute("current", number);
        if(number < 1){
            return "redirect:/admin/orders/1";
        }
        int max = (int) Math.ceil(ordersService.getAll().size() / 2.0);
        max = max==0?1:max;
        if(number > max){
            return "redirect:/admin/orders/"+max;
        }
        if(!ordersService.getAll().isEmpty()){
            Page<Order> orders = ordersRepository.findAll(PageRequest.of((number-1), 2));
            model.addAttribute("orders", orders);
            return "admin/orders";
        }
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
        Order order = ordersService.getById(id);
        additionalServicesId.remove(0);
        List<AdditionalService> additionalServices = new ArrayList<>();

        try {
            for (Long aLong : additionalServicesId) {
                additionalServices.add(additionalServicesService.getById(aLong));
            }
        }catch (Exception e){}

        double totalPrice = 0;
        if(type){
            try{
                for (AdditionalService additionalService : additionalServices) {
                    totalPrice +=additionalService.getPrice();
                }
            }catch (Exception e){
            }
            if(transport.equals("plane")) totalPrice += 0.5*Double.valueOf(weight)+1000;
            else if(transport.equals("ship")) totalPrice += 0.3*Double.valueOf(weight)+500;
            else totalPrice += 800;
        }else {
            try{
                for (AdditionalService additionalService : additionalServices) {
                    totalPrice+=additionalService.getPrice();
                }
            }catch (Exception e){
            }
            if(transport.equals("plane"))totalPrice += 0.1*Double.valueOf(price)+0.5*Double.valueOf(weight)+1000;
            else if(transport.equals("ship"))totalPrice += 0.05*Double.valueOf(price)+0.3*Double.valueOf(weight)+500;
            else totalPrice += 800;
        }
        order.setStatus(Status.READY_FOR_PAYMENT.getStatus());
        order.setTotalPrice(totalPrice);
        ordersService.save(order);
        return "redirect:/admin/orders/edit/"+id;
    }

    @GetMapping("/admin/test/")
    public String test(){
        return "admin/test";
    }
}
