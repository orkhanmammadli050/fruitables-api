package az.edu.itbrains.fruitables.dtos.contact;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactDto {

    @NotBlank(message = "Ad boş ola bilməz")
    private String name;

    @NotBlank(message = "Email boş ola bilməz")
    @Email(message = "Email formatı düzgün deyil")
    private String email;

    @NotBlank(message = "Mesaj boş ola bilməz")
    private String message;
}