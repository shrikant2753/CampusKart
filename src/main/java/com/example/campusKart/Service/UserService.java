package com.example.campusKart.Service;

import com.example.campusKart.EntryDTOs.UserEntryDto;

public interface UserService {
    String addUser(UserEntryDto userEntryDto) throws Exception;
}
