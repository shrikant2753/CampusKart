package com.example.campusKart.Request.EntryDto;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class SendRequestEntryDto {
    private ObjectId sentBy;
    private ObjectId sentTo;
}
