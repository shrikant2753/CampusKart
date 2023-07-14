package com.example.campusKart.Request.Controller;

import com.example.campusKart.Request.Entity.Request;
import com.example.campusKart.Request.EntryDto.RequestEntryDto;
import com.example.campusKart.Request.Service.RequestService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class RequestController {
    @Autowired
    private RequestService requestService;

    @PostMapping("/send-request")
    public ResponseEntity<String> sendRequest(@RequestBody RequestEntryDto sendRequestEntryDto){
        try{
            String response = requestService.sendRequest(sendRequestEntryDto);
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            String response = e.getMessage();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/accept-request")
    public ResponseEntity<String> acceptRequest(@RequestParam ObjectId id){
        try{
            String response = requestService.acceptRequest(id);
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            String response = e.getMessage();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/reject-request")
    public ResponseEntity<String> rejectRequest(@RequestParam ObjectId id){
        try{
            String response = requestService.rejectRequest(id);
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            String response = e.getMessage();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-request")
    public ResponseEntity<String> deleteRequest(@RequestParam ObjectId id){
        try{
            String response = requestService.deleteRequest(id);
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            String response = e.getMessage();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-send-request")
    public ResponseEntity<?> getSendRequest(ObjectId userId){
        try{
            List<Request> response = requestService.getSendRequest(userId);
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            String response = e.getMessage();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-received-request")
    public ResponseEntity<?> getReceivedRequest(ObjectId userId){
        try{
            List<Request> response = requestService.getSendRequest(userId);
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            String response = e.getMessage();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-accepted-request")
    public ResponseEntity<?> getAcceptedRequest(ObjectId userId){
        try{
            List<Request> response = requestService.getSendRequest(userId);
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            String response = e.getMessage();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
