package com.example.campusKart.Controller;

import com.example.campusKart.EntryDTOs.UserEntryDto;
import com.example.campusKart.Service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }


    @PostMapping("/add")
    public ResponseEntity<String> addUser(@Valid @RequestBody UserEntryDto userEntryDto, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
        }
        try{
            String response = userServiceImpl.addUser(userEntryDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            String response = e.getMessage();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
