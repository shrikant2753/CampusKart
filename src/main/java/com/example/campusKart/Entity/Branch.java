package com.example.campusKart.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "branch")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Branch {
    private String branchName;
}
