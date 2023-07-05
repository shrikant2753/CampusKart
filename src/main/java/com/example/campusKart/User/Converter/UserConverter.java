package com.example.campusKart.User.Converter;

import com.example.campusKart.User.Entity.User;
import com.example.campusKart.User.EntryDTOs.UserEntryDto;
import com.example.campusKart.User.EntryDTOs.UserLoginDto;
import com.example.campusKart.User.ResponseDTOs.UserLoginResponseDto;
import org.bson.types.ObjectId;

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

    public static UserLoginResponseDto convertUserLoginDtoToUserResponse(User user, String objectId) {
        return UserLoginResponseDto.builder()
                .id(objectId)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .mobile(user.getMobile())
                .collegeName(user.getCollegeName())
                .branch(user.getBranch())
                .year(user.getYear())
                .build();
    }
}
