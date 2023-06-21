package com.example.campusKart.Service.impl;

import com.example.campusKart.Converter.UserConverter;
import com.example.campusKart.Entity.User;
import com.example.campusKart.EntryDTOs.UserEntryDto;
import com.example.campusKart.Repository.UserRepository;
import com.example.campusKart.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String addUser(UserEntryDto userEntryDto) throws Exception {
//        User existedUser = userRepository.findByEmail(userEntryDto.getEmail());
//        if(existedUser!=null){
//            throw new Exception("Email already exist");
//        }

        User user = UserConverter.convertDtoToEntity(userEntryDto);
        userRepository.save(user);
        return user.toString();
    }
}
