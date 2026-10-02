package az.edu.itbrains.fruitables.dtos.user;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDashboardDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private boolean enabled;
    private double cashback;
}
