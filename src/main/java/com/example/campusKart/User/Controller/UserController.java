package com.example.campusKart.User.Controller;

import com.example.campusKart.User.EntryDTOs.*;
import com.example.campusKart.User.Payload.LoginResponse;
import com.example.campusKart.User.ResponseDTOs.UserLoginResponseDto;
import com.example.campusKart.User.Service.UserService;
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
    UserService userService;


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
            String response = userService.addUser(userEntryDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(org.springframework.dao.DuplicateKeyException e){
            return new ResponseEntity<>("Email already exist, please try to login", HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e) {
            String response = e.getMessage();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserLoginDto userLoginDto) throws Exception {
        try{
            UserLoginResponseDto userLoginResponseDto = userService.login(userLoginDto);
            LoginResponse loginResponse = new LoginResponse("User login successfully", userLoginResponseDto);
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        }
        catch(Exception e){
            String response = e.getMessage();
            LoginResponse loginResponse = new LoginResponse(response, null);
            return new ResponseEntity<>(loginResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-info")
    public ResponseEntity<String> updateUserInfo(@RequestBody UpdateUserInfoDto updateUserInfoDto){
       try{
           String response = userService.updateUserInfo(updateUserInfoDto);
           return new ResponseEntity<>(response, HttpStatus.OK);
       }
       catch (Exception e){
           String response = e.getMessage();
           return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
       }
    }

    @PutMapping("/update-mobile")
    public ResponseEntity<String> updateMobile(@RequestBody UpdateMobileDto updateMobileDto){
        try{
            String response = userService.updateMobileNumber(updateMobileDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e){
            String response = e.getMessage();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-email")
    public ResponseEntity<String> updateEmail(@RequestBody UpdateEmailDto updateEmailDto){
        try{
            String response = userService.updateEmail(updateEmailDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e){
            String response = e.getMessage();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
