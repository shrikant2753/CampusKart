package com.example.campusKart.User.Service.impl;

import com.example.campusKart.User.Converter.UserConverter;
import com.example.campusKart.User.Entity.User;
import com.example.campusKart.User.EntryDTOs.UserEntryDto;
import com.example.campusKart.User.Repository.UserRepository;
import com.example.campusKart.User.Service.UserService;
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
        Pattern p = Pattern.compile("((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,20})");
        Matcher m= p.matcher(userEntryDto.getPassword());
        if(!m.matches()){
            throw new Exception("Password contain minimum one small letter, one capital letter, one digit and one spacial character ");
        }
        User user = UserConverter.convertDtoToEntity(userEntryDto);
        userRepository.save(user);
        return "user created successfully";
    }
}
