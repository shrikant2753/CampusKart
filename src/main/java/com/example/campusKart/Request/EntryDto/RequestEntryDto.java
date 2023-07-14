package com.example.campusKart.Request.EntryDto;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class RequestEntryDto {
    private ObjectId sentBy;
    private ObjectId sentTo;
}
