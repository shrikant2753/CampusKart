package com.example.campusKart.Repository;

import com.example.campusKart.Entity.College;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeRepository extends MongoRepository<College, ObjectId> {
}
