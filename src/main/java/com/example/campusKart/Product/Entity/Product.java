package com.example.campusKart.Product.Entity;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection="product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    private ObjectId _id;
    @NonNull
    private ObjectId user_id;
    @NonNull
    private String type;

    private int quantity;

    private double costPrice;

    private double sellingPrice;
    @NonNull
    private String relDepartment;
    @NonNull
    private int relYear;
    @NonNull
    private int relSemester;
    @NonNull
    private String description;

    private ArrayList<String>imagePath = new ArrayList<>();
    private ArrayList<ObjectId> userBookmarkedProduct = new ArrayList<>();
}
