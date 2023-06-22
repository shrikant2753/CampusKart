package com.example.campusKart.Service.impl;

import com.example.campusKart.Converter.UserConverter;
import com.example.campusKart.Entity.User;
import com.example.campusKart.EntryDTOs.UserEntryDto;
import com.example.campusKart.Repository.UserRepository;
import com.example.campusKart.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Pattern p = Pattern.compile("((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,20})");
        Matcher m= p.matcher(userEntryDto.getPassword());
        if(!m.matches()){
            throw new Exception("Password contain min one small letter, one capital letter, one digit and one spacial character ");
        }
        User user = UserConverter.convertDtoToEntity(userEntryDto);
        userRepository.save(user);
        return user.toString();
    }
}
