package az.edu.itbrains.fruitables.services;

import az.edu.itbrains.fruitables.dtos.auth.RegisterDto;

public interface EmailService {

    boolean getConfirmationEmail(String token, RegisterDto registerDto);
    boolean sendContactMessage(String name, String email, String message);
}
