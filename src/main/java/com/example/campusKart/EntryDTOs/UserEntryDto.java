package com.example.campusKart.EntryDTOs;

import com.example.campusKart.Entity.Branch;
import com.example.campusKart.Entity.College;
import com.example.campusKart.Enum.Year;
import lombok.Data;

@Data
public class UserEntryDto {
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String password;
    private College collegeName;
    private Year year;
    private Branch branch;
}
