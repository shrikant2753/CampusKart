package com.example.campusKart.Product.Service;

import com.example.campusKart.Product.EntryDTOs.ProductEntryDto;
import com.example.campusKart.Product.EntryDTOs.UpdateProductDto;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    String addProduct(ProductEntryDto productEntryDto) throws Exception;
    String uploadImage(String path, MultipartFile file, ObjectId productId) throws Exception;
    String updateProduct(UpdateProductDto updateProductDto);
}
