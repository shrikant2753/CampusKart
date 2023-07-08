package com.example.campusKart.Request.Convertor;

import com.example.campusKart.Request.Entity.Request;
import com.example.campusKart.Request.EntryDto.RequestEntryDto;

public class RequestConvertor {
    public static Request convertDtoToRequestEntity(RequestEntryDto requestEntryDto){
        return Request.builder().
                sentBy(requestEntryDto.getSentBy()).
                sentTo(requestEntryDto.getSentTo()).
                isAccepted(requestEntryDto.isAccepted()).
                isRejected(requestEntryDto.isRejected()).
                build();
    }
}
