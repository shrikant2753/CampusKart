package com.example.campusKart.Product.Service.Impl;

import com.example.campusKart.Product.Convertor.ProductConvertor;
import com.example.campusKart.Product.Entity.Product;
import com.example.campusKart.Product.EntryDTOs.ProductEntryDto;
import com.example.campusKart.Product.EntryDTOs.UpdateProductDto;
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
import java.nio.file.Path;
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

        if(productEntryDto.getQuantity()<1){
            throw new Exception("Minimum quantity required for the product is 1");
        }

        if(productEntryDto.getSellingPrice() <= 0){
            throw new Exception("Selling price should be positive decimal value");
        }

        if(productEntryDto.getCostPrice() <= 0){
            throw new Exception("Cost price should be positive decimal value");
        }

        Optional<User> optionalUser = userRepository.findById(productEntryDto.getUser_id());
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            Product product = ProductConvertor.convertDtoToProductEntity(productEntryDto);
//            ArrayList<String> l = new ArrayList<>();
//            product.setImagePath(l);
            Product savedProduct = productRepository.save(product);
            List<ObjectId> productList = user.getProductList();
            productList.add(savedProduct.get_id());
            user.setProductList(productList);
            userRepository.save(user);

            return "Product added successfully";
        }
        throw new IllegalArgumentException("User not found with ID: " + productEntryDto.getUser_id());
    }

    @Override
    public String uploadImage(String path, MultipartFile file, ObjectId productId) throws Exception {
        // Check if the uploaded file is empty
        if (file.isEmpty()) {
            throw new Exception("Uploaded file is empty");
        }

        // Check if the uploaded file is an image
        if (!isImageFile(file)) {
            throw new Exception("Uploaded file is not a valid image");
        }

        //file name
        String name = file.getOriginalFilename();

        //random name generator
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

        //full path
        String filePath = path + File.separator + fileName;

        //create folder if not created
        Path imageFolder = Paths.get(path);
        if (!Files.exists(imageFolder)) {
            Files.createDirectories(imageFolder);
        }

        //file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        Product product = productRepository.findById(productId).get();
        ArrayList<String> imagePath = product.getImagePath();
        if(imagePath.size()<2)
            imagePath.add(filePath);
        else{
            throw new Exception("Only two images can be uploaded");
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

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType != null) {
            return contentType.startsWith("image/");
        }
        return false;
    }

    public String updateProduct(UpdateProductDto updateProductDto){
        Optional<Product> optionalProduct = productRepository.findById(updateProductDto.getProduct_id());
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            if(updateProductDto.getQuantity() > 1){
                product.setQuantity(updateProductDto.getQuantity());
            }
            if(updateProductDto.getCostPrice()>0){
                product.setCostPrice(updateProductDto.getCostPrice());
            }
            if(updateProductDto.getSellingPrice()>0){
                product.setSellingPrice(updateProductDto.getSellingPrice());
            }
            if(updateProductDto.getType()!=null){
                product.setType(updateProductDto.getType());
            }
            if(updateProductDto.getRelDepartment()!=null){
                product.setRelDepartment(updateProductDto.getRelDepartment());
            }
            if(updateProductDto.getDescription()!=null){
                product.setDescription(updateProductDto.getDescription());
            }
            if(updateProductDto.getRelSemester()>0 && updateProductDto.getRelSemester()<9){
                product.setRelSemester(updateProductDto.getRelSemester());
            }
            if(updateProductDto.getRelYear()>0 && updateProductDto.getRelYear()<5){
                product.setRelYear(updateProductDto.getRelYear());
            }
            productRepository.save(product);
            return "product updated successfully";
        }
        throw new IllegalArgumentException("product not found ");
    }
}
