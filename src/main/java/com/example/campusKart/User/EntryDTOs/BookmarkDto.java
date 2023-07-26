package com.example.campusKart.User.EntryDTOs;

import lombok.Data;
import org.bson.types.ObjectId;
@Data
public class BookmarkDto {
    private ObjectId userId;
    private  ObjectId productId;
}
