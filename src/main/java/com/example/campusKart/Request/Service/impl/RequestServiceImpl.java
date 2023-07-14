package com.example.campusKart.Request.Service.impl;

import com.example.campusKart.Request.Convertor.RequestConvertor;
import com.example.campusKart.Request.Entity.Request;
import com.example.campusKart.Request.EntryDto.RequestEntryDto;
import com.example.campusKart.Request.Repository.RequestRepository;
import com.example.campusKart.Request.Service.RequestService;
import com.example.campusKart.User.Entity.User;
import com.example.campusKart.Request.Exception.DatabaseException;
import com.example.campusKart.Request.Exception.UserNotFoundException;
import com.example.campusKart.User.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RequestRepository requestRepository;

    @Override
    public synchronized String sendRequest(RequestEntryDto sendRequestEntryDto) throws UserNotFoundException, DatabaseException {
        ObjectId sendTo = sendRequestEntryDto.getSentTo();
        ObjectId sendBy = sendRequestEntryDto.getSentBy();

        try {
            Optional<User> senderOptional = userRepository.findById(sendBy);
            Optional<User> recipientOptional = userRepository.findById(sendTo);

            if (senderOptional.isPresent() && recipientOptional.isPresent()) {
                Request request = RequestConvertor.convertDtoToRequestEntity(sendRequestEntryDto);

                // Validate the request here before saving if required

                Request savedRequest = requestRepository.save(request);

                User sender = senderOptional.get();
                synchronized (sender) {
                    List<ObjectId> senderRequestList = sender.getRequestList();
                    senderRequestList.add(savedRequest.getId());
                    sender.setRequestList(senderRequestList);
                    userRepository.save(sender);
                }

                User recipient = recipientOptional.get();
                synchronized (recipient) {
                    List<ObjectId> recipientRequestList = recipient.getRequestList();
                    recipientRequestList.add(savedRequest.getId());
                    recipient.setRequestList(recipientRequestList);
                    userRepository.save(recipient);
                }
                return "Request sent successfully";
            }
            throw new UserNotFoundException("One or both users do not exist");
        } catch (UserNotFoundException e) {
            throw e; // Re-throw the UserNotFoundException
        } catch (Exception e) {
            throw new DatabaseException("Database operation failed", e);
        }
    }

    @Override
    public String acceptRequest(ObjectId requestId) throws DatabaseException {

        Optional<Request> optional = requestRepository.findById(requestId);

        try{
            if (optional.isPresent()) {
                Request request = optional.get();
                synchronized (request) {
                    request.setAccepted(true);
                    requestRepository.save(request);
                }
                return "request accepted";
            }
            throw new UserNotFoundException("User not found");
        }
        catch(Exception e){
            throw new DatabaseException("Database operations failed", e);
        }
    }

    @Override
    public String rejectRequest(ObjectId requestId) throws DatabaseException {
        try {
            Optional<Request> optional = requestRepository.findById(requestId);

            if (optional.isPresent()) {
                Request request = optional.get();
                synchronized (request){
                    request.setRejected(true);
                    request.setAccepted(false);
                    requestRepository.save(request);
                }
                return "request rejected";
            }
            throw new UserNotFoundException("User not Found");
        }
        catch(Exception e){
            throw new DatabaseException("Database operations failed", e);
        }
    }

    public String deleteRequest(ObjectId id) throws DatabaseException {
        try {
            Optional<Request> optional = requestRepository.findById(id);

            if (optional.isPresent()) {
                Request request = optional.get();
                ObjectId sendTo = request.getSentTo();
                ObjectId sendBy = request.getSentBy();

                synchronized (request) {
                    requestRepository.deleteById(request.getId());
                }

                if (userRepository.existsById(sendBy) && userRepository.existsById(sendTo)) {
                    User user = userRepository.findById(sendBy).orElseThrow(() -> new UserNotFoundException("User not found"));
                    User user1 = userRepository.findById(sendTo).orElseThrow(() -> new UserNotFoundException("User not found"));

                    synchronized (user) {
                        List<ObjectId> l = new ArrayList<>(user.getRequestList());
                        l.remove(request.getId());
                        user.setRequestList(l);
                        userRepository.save(user);
                    }

                    synchronized (user1) {
                        List<ObjectId> l1 = new ArrayList<>(user1.getRequestList());
                        l1.remove(request.getId());
                        user1.setRequestList(l1);
                        userRepository.save(user1);
                    }

                    // Save both users using Arrays.asList()
                    userRepository.saveAll(Arrays.asList(user, user1));

                    return "Request deleted successfully";
                } else {
                    throw new UserNotFoundException("User not found");
                }
            } else {
                throw new UserNotFoundException("Request not found");
            }
        } catch (Exception e) {
            throw new DatabaseException("Database operations failed", e);
        }
    }

    @Override
    public List<Request> getSendRequest(ObjectId userId) throws DatabaseException {
        return getRequestListByUserAndStatus(userId, false, false);
    }

    @Override
    public List<Request> getReceivedRequest(ObjectId userId) throws DatabaseException {
        return getRequestListByUserAndStatus(userId, true, false);
    }

    @Override
    public List<Request> getAcceptedRequest(ObjectId userId) throws DatabaseException {
        return getRequestListByUserAndStatus(userId, false, true);
    }

    private List<Request> getRequestListByUserAndStatus(ObjectId userId, boolean isReceived, boolean isAccepted)
            throws DatabaseException {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                synchronized (user) {
                    List<ObjectId> requestList = user.getRequestList();
                    if (requestList.isEmpty()) {
                        throw new RuntimeException(isReceived ? "No request received" : "No request send");
                    }

                    List<Request> requestListFiltered = new ArrayList<>();

                    for (ObjectId requestId : requestList) {
                        Request request = requestRepository.findById(requestId).orElse(null);
                        if (request != null && ((isReceived && userId.equals(request.getSentTo()))
                                || (!isReceived && userId.equals(request.getSentBy())))
                                && (isAccepted == request.isAccepted())) {
                            requestListFiltered.add(request);
                        }
                    }
                    return requestListFiltered;
                }
            }
        } catch (Exception e) {
            throw new DatabaseException("Error occurred", e);
        }
        return Collections.emptyList();
    }
}
