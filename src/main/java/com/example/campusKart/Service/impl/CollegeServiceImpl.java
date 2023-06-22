package com.example.campusKart.Service.impl;

import com.example.campusKart.Entity.College;
import com.example.campusKart.Repository.CollegeRepository;
import com.example.campusKart.Service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollegeServiceImpl implements CollegeService {
    @Autowired
    CollegeRepository collegeRepository;

    @Override
    public String addCollege(String collegeName) {
        College college = new College(collegeName);
        collegeRepository.save(college);
        return "College Added Successfully";
    }
}
