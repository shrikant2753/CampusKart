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
    private UserRepository userRepository;

    public UserServiceImpl() {
    }

    @Override
    public String addUser(UserEntryDto userEntryDto) throws Exception {

//        E-mail address validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher emailMatch = emailPattern.matcher(userEntryDto.getEmail());
        if(!emailMatch.matches()){
            throw new Exception("Email is not valid");
        }

//        Mobile number pattern matcher
        Pattern mob = Pattern.compile("[6-9][0-9]{9}");
        Matcher mobMatch = mob.matcher(userEntryDto.getMobile());
        if(!mobMatch.matches()){
            throw new Exception("Mobile number is not valid");
        }

//        Password pattern matcher
        Pattern pass = Pattern.compile("((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,20})");
        Matcher passMatch = pass.matcher(userEntryDto.getPassword());
        if(!passMatch.matches()){
            throw new Exception("Password contain atleast 1 small letter, 1 capital letter, 1 digit and 1 special character");
        }

        User user = UserConverter.convertDtoToEntity(userEntryDto);
        userRepository.save(user);
        return user.toString();
    }
}
