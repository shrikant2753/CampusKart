package com.example.campusKart.Controller;

import com.example.campusKart.Service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/college")
public class CollegeController {
    @Autowired
    CollegeService collegeService;

    @PostMapping("/add")
    public String addCollege(@RequestParam String collegeName){
        return collegeService.addCollege(collegeName);
    }
}
