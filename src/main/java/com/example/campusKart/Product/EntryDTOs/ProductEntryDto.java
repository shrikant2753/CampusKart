package com.example.campusKart.Product.EntryDTOs;

import lombok.Data;

@Data
public class ProductEntryDto {
    private String type;
    private int quantity;
    private double costPrice;
    private double sellingPrice;
    private String relDepartment;
    private int relYear;
    private int relSemester;
    private String description;
}
