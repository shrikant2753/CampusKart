package com.example.campusKart.Product.Service.Impl;

import com.example.campusKart.Product.Convertor.ProductConvertor;
import com.example.campusKart.Product.Entity.Product;
import com.example.campusKart.Product.EntryDTOs.ProductEntryDto;
import com.example.campusKart.Product.Repository.ProductRepository;
import com.example.campusKart.Product.Service.ProductService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public String addProduct(ProductEntryDto productEntryDto) throws Exception {

        double sellingPrice = productEntryDto.getSellingPrice();
        if(sellingPrice<0){
            throw new Exception("Selling price should not be negative");
        }

        double costPrice = productEntryDto.getCostPrice();
        if(costPrice < 0){
            throw new Exception("Cost price should not be negative");
        }

        Product product = ProductConvertor.convertDtoToProductEntity(productEntryDto);
        productRepository.save(product);
        return product.toString();
    }

    @Override
    public String uploadImage(String path, MultipartFile file, ObjectId productId) throws IOException {
        //file name
        String name = file.getOriginalFilename();

        //random name generator
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

        //full path
        String filePath = path + File.separator + fileName;

        //create folder if not created
        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        //file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        Product product = productRepository.findById(productId).get();
        List<String> imagePath = product.getImagePath();
        imagePath.add(filePath);
        productRepository.save(product);
        return name;
    }
}
