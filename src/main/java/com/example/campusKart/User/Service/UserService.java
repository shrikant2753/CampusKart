package com.example.campusKart.Service;

import com.example.campusKart.EntryDTOs.UserEntryDto;

import javax.validation.ValidationException;

public interface UserService {
    String addUser(UserEntryDto userEntryDto) throws Exception;
}
