package spring.advanced.schedulerjpa.user.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record UserUpdateRequestDto(

        @Pattern(regexp = "^[a-zA-Z0-9]+$",
                message = "{validation.username}"
        )
        String username,

        @Email
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
                message = "{validation.email}"
        )
        String email,

        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^*_+|<>?:{}])[A-Za-z\\d~!@#$%^*_+|<>?:{}]{4,}$",
                message = "{validation.password}")
        String password
) {
}
