package com.example.campusKart.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "college")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class College {
    private String collegeName;
}
