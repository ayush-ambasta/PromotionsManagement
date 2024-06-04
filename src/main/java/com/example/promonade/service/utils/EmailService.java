package com.example.promonade.service.utils;

import com.example.promonade.dto.request.EmailDetails;

public interface EmailService {
    String sendMail(EmailDetails details);
}
