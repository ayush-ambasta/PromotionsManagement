package com.example.promonade.service.utils;

import com.example.promonade.exceptions.userExceptions.UserNotFoundException;
import com.example.promonade.models.User;
import com.example.promonade.repositories.UserRepository;
import com.example.promonade.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Component
@AllArgsConstructor
public class GeneralUtils {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    public User getUserFromAuthToken(String headerAuth){
        String authToken = headerAuth.substring(7, headerAuth.length());
        String username = jwtUtils.getUserNameFromJwtToken(authToken);
        System.out.println("Username: "+username);
        Optional<User> optionalCreatedBy = userRepository.findByUsername(username);
        if(optionalCreatedBy.isEmpty()){
            throw new UserNotFoundException(String.format("User with the specified username - %s is not found!", username));
        }
        return optionalCreatedBy.get();
    }

    public String convertToCronExpression(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int second = calendar.get(Calendar.SECOND);
        int minute = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1; // Month starts from 0

        // Construct the cron expression
        String cronExpression = String.format("%d %d %d %d %d *", second, minute, hour, dayOfMonth, month);
        return cronExpression;
    }
}
