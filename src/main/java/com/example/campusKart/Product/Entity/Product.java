package com.example.campusKart.Product.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation="product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    private String type;
    private int quantity;
    private double costPrice;
    private double sellingPrice;
    private String relDepartment;
    private int relYear;
    private int relSemester;
    private String description;
}
