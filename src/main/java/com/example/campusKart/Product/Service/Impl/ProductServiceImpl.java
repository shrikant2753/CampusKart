package com.example.campusKart.Product.Service.Impl;

import com.example.campusKart.Product.Convertor.ProductConvertor;
import com.example.campusKart.Product.Entity.Product;
import com.example.campusKart.Product.EntryDTOs.ProductEntryDto;
import com.example.campusKart.Product.Repository.ProductRepository;
import com.example.campusKart.Product.Service.ProductService;
import com.example.campusKart.User.Entity.User;
import com.example.campusKart.User.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public String addProduct(ProductEntryDto productEntryDto) throws Exception {

        double sellingPrice = productEntryDto.getSellingPrice();
        if(sellingPrice < 0){
            throw new Exception("Selling price should not be negative");
        }

        double costPrice = productEntryDto.getCostPrice();
        if(costPrice < 0){
            throw new Exception("Cost price should not be negative");
        }

        Optional<User> optionalUser = userRepository.findById(productEntryDto.getUser_id());
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            Product product = ProductConvertor.convertDtoToProductEntity(productEntryDto);
            ArrayList<String> l = new ArrayList<>();
            product.setImagePath(l);
            Product savedProduct = productRepository.save(product);
            List<ObjectId> productList = user.getProductList();
            productList.add(savedProduct.get_id());
            user.setProductList(productList);
            userRepository.save(user);

            return product.toString();
        }
        throw new IllegalArgumentException("User not found with ID: " + productEntryDto.getUser_id());
    }

    @Override
    public String uploadImage(String path, MultipartFile file, ObjectId productId) throws Exception {
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
        ArrayList<String> imagePath = product.getImagePath();
        imagePath.add(filePath);
//        product.setImagePath(imagePath);
        for(String s : imagePath){
            System.out.println(s);
        }

        Query query = new Query(Criteria.where("_id").is(productId));
        Update updateDefinition = new Update().set("imagePath", imagePath);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);

        Product updatedProduct = mongoTemplate.findAndModify(query, updateDefinition, options, Product.class);

        if (updatedProduct == null) {
            throw new Exception("Failed to update product image path");
        }
        return name;
    }
}
