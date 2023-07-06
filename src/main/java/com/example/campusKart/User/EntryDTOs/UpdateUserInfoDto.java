package com.example.campusKart.User.EntryDTOs;

import lombok.Data;

@Data
public class UpdateUserInfoDto {
    private String email;
    private String firstName;
    private String lastName;
    private String collegeName;
    private int year;
    private String branch;
}
