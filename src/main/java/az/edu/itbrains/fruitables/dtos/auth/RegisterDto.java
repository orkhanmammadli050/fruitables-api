package az.edu.itbrains.fruitables.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class RegisterDto {

    @Length(min = 3, max = 20, message = "Ad 3 ilə 20 simvol arasında olmalıdır")
    private String firstname;

    @Length(min = 3, max = 20, message = "Soyad 3 ilə 20 simvol arasında olmalıdır")
    private String lastname;

    @Email(message = "Etibarsız e-poçt ünvanı")
    private String email;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$",
            message = "Şifrə ən az 8 simvol olmalı, böyük/kiçik hərf, rəqəm və xüsusi simvol ehtiva etməlidir")
    private String password;
}
