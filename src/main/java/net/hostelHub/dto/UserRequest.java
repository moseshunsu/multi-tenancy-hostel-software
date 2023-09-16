package net.hostelHub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotNull(message = "name must not be null")
    @Pattern(regexp = "^(?=.*\\s).{2,}$",
            message = "name must be more than 2 characters and must also include a whitespace character")
    private String name;

    @NotNull(message = "username must not be null")
    @Pattern(regexp = "^.{2,}$", message = "username must be more than 2 characters")
    private String username;

    @NotNull(message = "email must not be null")
    @Email
    private String email;

    @Pattern(regexp = "^(?!.*\\s)\\d{10,20}$",
            message = """
                    digits must not be less than 10 digits or more than 20 digits +
                    and must not include a white space character
                    """)
    private String phoneNumber;

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+*=!-_])(?!.*\\s).{8,16}$",
            message = """
                    password must meet the following conditions:
                    must include at least an upper and lowercase character;
                    must include at least one special character;
                    must not include a white space character;
                    length must be between 8 and 16 characters.
                    """
    )
    private String password;

}
