package com.example.campusKart.Product.Convertor;


import com.example.campusKart.Product.Entity.Product;
import com.example.campusKart.Product.EntryDTOs.ProductEntryDto;

public class ProductConvertor {

    public static Product convertDtoToProductEntity(ProductEntryDto productEntryDto){
        return Product.builder().
                type(productEntryDto.getType()).
                quantity(productEntryDto.getQuantity()).
                costPrice(productEntryDto.getCostPrice()).
                sellingPrice(productEntryDto.getSellingPrice()).
                relDepartment(productEntryDto.getRelDepartment()).
                relYear(productEntryDto.getRelYear()).
                relSemester(productEntryDto.getRelSemester()).
                description(productEntryDto.getDescription()).
                build();
    }
}
