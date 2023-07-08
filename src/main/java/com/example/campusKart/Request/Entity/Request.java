package com.example.campusKart.Request.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Request")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Request {
    private ObjectId sentBy;
    private ObjectId sentTo;
    private boolean isAccepted;
    private boolean isRejected;
}
