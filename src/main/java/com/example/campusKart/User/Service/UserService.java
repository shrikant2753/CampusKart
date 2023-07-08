package com.example.campusKart.User.Service;

import com.example.campusKart.User.EntryDTOs.*;
import com.example.campusKart.User.ResponseDTOs.UserLoginResponseDto;

public interface UserService {
    String addUser(UserEntryDto userEntryDto) throws Exception;
    UserLoginResponseDto login(UserLoginDto userLoginDto) throws Exception;

    String updateUserInfo(UpdateUserInfoDto updateUserInfoDto);

    String updateMobileNumber(UpdateMobileDto updateMobileDto);

    String updateEmail(UpdateEmailDto updateEmailDto);
}
