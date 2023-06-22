package com.example.campusKart.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "branch")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Branches {
    private String branch;
}
