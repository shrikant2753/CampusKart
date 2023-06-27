package com.example.campusKart.Converter;

import com.example.campusKart.Entity.User;
import com.example.campusKart.EntryDTOs.UserEntryDto;

public class UserConverter {
    public static User convertDtoToEntity(UserEntryDto userEntryDto){
        return User.builder().
                firstName(userEntryDto.getFirstName()).
                lastName(userEntryDto.getLastName()).
                email(userEntryDto.getEmail()).
                mobile(userEntryDto.getMobile()).
                password(userEntryDto.getPassword()).
                collegeName(userEntryDto.getCollegeName()).
                year(userEntryDto.getYear()).
                branch(userEntryDto.getBranch()).
                build();
    }
}
