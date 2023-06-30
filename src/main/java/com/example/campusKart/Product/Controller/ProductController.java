package com.example.campusKart.Product.Controller;

import com.example.campusKart.Product.EntryDTOs.ProductEntryDto;
import com.example.campusKart.Product.Service.ProductService;
import com.example.campusKart.User.EntryDTOs.UserEntryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("add")
    public ResponseEntity<String> addProduct(@RequestBody ProductEntryDto productEntryDto){
        try{
            String response = productService.addProduct(productEntryDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            String response = e.getMessage();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
