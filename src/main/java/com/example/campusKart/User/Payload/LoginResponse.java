package com.example.campusKart.User.Payload;

import com.example.campusKart.User.ResponseDTOs.UserLoginResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    String message;
    UserLoginResponseDto userLoginResponseDto;
}
