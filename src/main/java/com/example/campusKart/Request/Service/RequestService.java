package com.example.campusKart.Request.Service;

import com.example.campusKart.Request.Entity.Request;
import com.example.campusKart.Request.EntryDto.RequestEntryDto;
import com.example.campusKart.Request.Exception.DatabaseException;
import org.bson.types.ObjectId;

import java.util.List;

public interface RequestService {
    String sendRequest(RequestEntryDto sendRequestEntryDto) throws Exception;
    String acceptRequest(ObjectId id) throws Exception;
    String rejectRequest(ObjectId id) throws Exception;
    String deleteRequest(ObjectId id) throws Exception;

    List<Request> getSendRequest(ObjectId userid) throws DatabaseException;
    List<Request> getReceivedRequest(ObjectId userid) throws DatabaseException;
    List<Request> getAcceptedRequest(ObjectId userid) throws DatabaseException;
}
