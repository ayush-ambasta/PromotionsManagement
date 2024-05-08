package com.example.promonade.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageResponse {
    private String message;
    private boolean success=false;

    public MessageResponse(String message){
        this.message = message;
    }
}
