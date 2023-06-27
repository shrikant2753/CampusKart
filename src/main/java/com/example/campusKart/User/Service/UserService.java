package com.example.campusKart.User.Service;

import com.example.campusKart.User.EntryDTOs.UserEntryDto;

public interface UserService {
    String addUser(UserEntryDto userEntryDto) throws Exception;
}
