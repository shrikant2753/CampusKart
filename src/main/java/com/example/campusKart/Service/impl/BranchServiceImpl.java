package com.example.campusKart.Service.impl;

import com.example.campusKart.Entity.Branch;
import com.example.campusKart.Repository.BranchRepository;
import com.example.campusKart.Service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BranchServiceImpl implements BranchService {
    @Autowired
    BranchRepository branchRepository;

    @Override
    public String addBranch(String branchName) {
        Branch branch = new Branch();
        branch.setBranchName(branchName);
        branchRepository.save(branch);
        return "Branch added successfully";
    }
}
