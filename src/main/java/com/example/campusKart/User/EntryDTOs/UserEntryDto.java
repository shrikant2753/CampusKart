package com.example.campusKart.User.EntryDTOs;

import lombok.Data;

@Data
public class UserEntryDto {
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String password;
    private String collegeName;
    private int year;
    private String branch;
}
