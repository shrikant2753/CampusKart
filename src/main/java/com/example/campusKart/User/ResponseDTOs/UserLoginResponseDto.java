package com.example.campusKart.User.ResponseDTOs;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@Builder
public class UserLoginResponseDto {
    private String id;
    private String firstName;
    private String lastName;
    private String mobile;
    private String collegeName;
    private int year;
    private String branch;
}
