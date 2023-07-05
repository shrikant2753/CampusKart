package com.example.campusKart.User.Entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Document(collation = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class User {
    @NotBlank(message = "First name is required")
    private String firstName;
    private String lastName;

    @NonNull
    @Indexed(unique = true)
    private String email;

    private String mobile;

    @NonNull
    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character")
    private String password;

    @NonNull
    private String collegeName;

    private int year;

    @NonNull
    private String branch;

    @CreatedDate
    private Date createdOn;

    @LastModifiedDate
    private Date updatedOn;
}