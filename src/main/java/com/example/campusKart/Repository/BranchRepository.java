package com.example.campusKart.Repository;

import com.example.campusKart.Entity.Branch;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends MongoRepository<Branch, ObjectId> {
}
