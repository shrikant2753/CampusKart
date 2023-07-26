package com.example.campusKart.User.Service.impl;

import com.example.campusKart.Product.Entity.Product;
import com.example.campusKart.Product.Repository.ProductRepository;
import com.example.campusKart.User.Converter.UserConverter;
import com.example.campusKart.User.Entity.User;
import com.example.campusKart.User.EntryDTOs.*;
import com.example.campusKart.User.Repository.UserRepository;
import com.example.campusKart.User.ResponseDTOs.UserLoginResponseDto;
import com.example.campusKart.User.Service.UserService;
import lombok.SneakyThrows;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String addUser(UserEntryDto userEntryDto) throws Exception {

//        email validator
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher emailMatch = emailPattern.matcher(userEntryDto.getEmail());
        if(!emailMatch.matches()){
            throw new Exception("Email is not valid");
        }

//      Mobile number pattern matcher
        Pattern mob = Pattern.compile("[6-9][0-9]{9}");
        Matcher mobMatch = mob.matcher(userEntryDto.getMobile());
        if(!mobMatch.matches()){
            throw new Exception("Mobile number is not valid");
        }

//        password validator
        Pattern p = Pattern.compile("((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,20})");
        Matcher m= p.matcher(userEntryDto.getPassword());
        if(!m.matches()){
            throw new Exception("Password contain minimum one small letter, one capital letter, one digit and one spacial character ");
        }
        User user = UserConverter.convertDtoToEntity(userEntryDto);
        userRepository.save(user);
        return "user created successfully";
    }

    public UserLoginResponseDto login(UserLoginDto userLoginDto) throws Exception {
        User user = userRepository.findByEmail(userLoginDto.getEmail());
        if(user==null){
            throw new Exception("Enter a valid email address");
        }
        if(!user.getPassword().equals(userLoginDto.getPassword())){
            throw new Exception("Incorrect password");
        }
        String objectId = user.get_id().toString();
        return UserConverter.convertUserLoginDtoToUserResponse(user, objectId);
    }

    @SneakyThrows
    @Override
    public String updateUserInfo(UpdateUserInfoDto updateUserInfoDto) {
        User user = userRepository.findByEmail(updateUserInfoDto.getEmail());
        if(user==null){
            throw new Exception("User does not exist with this email " + updateUserInfoDto.getEmail());
        }
        if(updateUserInfoDto.getFirstName()!=null)
            user.setFirstName(updateUserInfoDto.getFirstName());
        if(updateUserInfoDto.getLastName()!=null)
            user.setLastName(updateUserInfoDto.getLastName());
        if(updateUserInfoDto.getCollegeName()!=null)
            user.setCollegeName(updateUserInfoDto.getCollegeName());
        if(updateUserInfoDto.getYear()!=0)
            user.setYear(updateUserInfoDto.getYear());
        if(updateUserInfoDto.getBranch()!=null)
            user.setBranch(updateUserInfoDto.getBranch());
        userRepository.save(user);
        return "user info updated successfully";
    }

    @Override
    public String updateMobileNumber(UpdateMobileDto updateMobileDto) {
        Optional<User>optionalUser = Optional.ofNullable(userRepository.findByEmail(updateMobileDto.getEmail()));

        if(optionalUser.isPresent()){
            User user = optionalUser.get();

//            Mobile number pattern matcher
            Pattern mob = Pattern.compile("[6-9][0-9]{9}");
            Matcher mobMatch = mob.matcher(updateMobileDto.getMobile());
            if(!mobMatch.matches()){
                throw new IllegalArgumentException("Mobile number is not valid");
            }
            user.setMobile(updateMobileDto.getMobile());
            userRepository.save(user);
            return "Mobile number updated Successfully";
        }
        throw new IllegalArgumentException("User does not exist");

    }

    @Override
    public String updateEmail(UpdateEmailDto updateEmailDto) {
        Optional<User>optionalUser = Optional.ofNullable(userRepository.findByEmail(updateEmailDto.getEmail()));

        if(optionalUser.isPresent()){
            User user = optionalUser.get();

            //email validator
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";

            Pattern emailPattern = Pattern.compile(emailRegex);
            Matcher emailMatch = emailPattern.matcher(updateEmailDto.getEmail());
            if(!emailMatch.matches()){
                throw new IllegalArgumentException("Email is not valid");
            }
            user.setMobile(updateEmailDto.getNewEmail());
            userRepository.save(user);
            return "Email updated Successfully";
        }
        throw new IllegalArgumentException("User does not exist");
    }

    @Override
    public String bookmarkProduct(BookmarkDto bookmarkDto) {
        Optional<User>optionalUser = userRepository.findById(bookmarkDto.getUserId());
        Optional<Product>optionalProduct = productRepository.findById(bookmarkDto.getProductId());

        if(optionalUser.isPresent() && optionalProduct.isPresent()){
            Product product= optionalProduct.get();
            User user = optionalUser.get();
            ArrayList<ObjectId> bookmarkedProduct = user.getBookmarkedProduct();
            if(bookmarkedProduct.contains(bookmarkDto.getProductId())){
                return "product alredy in bookmark";
            }
            ArrayList<ObjectId> userBookmarked = product.getUserBookmarkedProduct();
            synchronized (this){
                bookmarkedProduct.add(bookmarkDto.getProductId());
                userBookmarked.add(bookmarkDto.getUserId());
                userRepository.save(user);
                productRepository.save(product);
            }
            return "product bookmarked successfully";
        }
        throw new RuntimeException("product or user is missing");
    }

    @Override
    public String unBookmarkedProduct(BookmarkDto bookmarkDto) {
        Optional<User>optionalUser = userRepository.findById(bookmarkDto.getUserId());
        Optional<Product>optionalProduct = productRepository.findById(bookmarkDto.getProductId());

        if(optionalUser.isPresent() && optionalProduct.isPresent()){
            Product product= optionalProduct.get();
            User user = optionalUser.get();
            ArrayList<ObjectId> bookmarkedProduct = user.getBookmarkedProduct();
            ArrayList<ObjectId> userBookmarked = product.getUserBookmarkedProduct();
            synchronized (this){
                bookmarkedProduct.remove(bookmarkDto.getProductId());
                userBookmarked.remove(bookmarkDto.getUserId());
                userRepository.save(user);
                productRepository.save(product);
            }
            return "product removed from bookmark";
        }
        throw new RuntimeException("product or user is missing");
    }
}
