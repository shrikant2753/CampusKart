package com.example.campusKart.Request.Repository;

import com.example.campusKart.Request.Entity.Request;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends MongoRepository<Request, ObjectId> {
}
