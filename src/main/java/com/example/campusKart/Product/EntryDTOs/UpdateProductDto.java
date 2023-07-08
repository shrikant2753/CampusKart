package com.example.campusKart.Product.EntryDTOs;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class UpdateProductDto {
    private ObjectId product_id;
    private String type;
    private int quantity;
    private double costPrice;
    private double sellingPrice;
    private String relDepartment;
    private int relYear;
    private int relSemester;
    private String description;
}
