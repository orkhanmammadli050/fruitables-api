package az.edu.itbrains.fruitables.services.impls;

import az.edu.itbrains.fruitables.dtos.auth.RegisterDto;
import az.edu.itbrains.fruitables.services.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static java.awt.SystemColor.text;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Override
    public boolean sendContactMessage(String name, String email, String message) {
        try {
            System.out.println("=== Yeni əlaqə mesajı ===");
            System.out.println("Ad: " + name);
            System.out.println("Email: " + email);
            System.out.println("Mesaj: " + message);
            System.out.println("========================");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean getConfirmationEmail(String token, RegisterDto registerDto) {
        try {
          Context context = new Context();
          context.setVariable("token", token);
          context.setVariable("email", registerDto.getEmail());
          context.setVariable("firstName", registerDto.getFirstname());
          context.setVariable("lastName", registerDto.getLastname());


          String htmlContent = templateEngine.process("email", context);


          MimeMessage message = mailSender.createMimeMessage();
          MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

          helper.setTo("elmore.bogan68@ethereal.email");
          helper.setFrom("elmore.bogan68@ethereal.email");
          helper.setSubject("Qeydiyyatınızı təsdiqləyin");
          helper.setText(htmlContent, true);
          mailSender.send(message);

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
