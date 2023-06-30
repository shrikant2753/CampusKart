package com.example.campusKart.Product.Service.Impl;

import com.example.campusKart.Product.Convertor.ProductConvertor;
import com.example.campusKart.Product.Entity.Product;
import com.example.campusKart.Product.EntryDTOs.ProductEntryDto;
import com.example.campusKart.Product.Repository.ProductRepository;
import com.example.campusKart.Product.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public String addProduct(ProductEntryDto productEntryDto) {
        Product product = ProductConvertor.convertDtoToProductEntity(productEntryDto);
        productRepository.save(product);
        return product.toString();
    }
}
