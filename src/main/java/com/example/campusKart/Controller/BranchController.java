package com.example.campusKart.Controller;

import com.example.campusKart.Service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/branch")
public class BranchController {
    @Autowired
    BranchService branchService;

    @PostMapping("/add")
    public String addCollege(@RequestParam String branchName){
        return branchService.addBranch(branchName);
    }
}
