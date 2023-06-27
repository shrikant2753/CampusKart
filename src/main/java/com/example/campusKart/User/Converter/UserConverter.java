package com.example.campusKart.User.Converter;

import com.example.campusKart.User.Entity.User;
import com.example.campusKart.User.EntryDTOs.UserEntryDto;

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
