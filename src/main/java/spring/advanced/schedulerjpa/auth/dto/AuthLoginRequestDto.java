package spring.advanced.schedulerjpa.auth.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AuthLoginRequestDto(
        @NotNull
        @Pattern(regexp = "^[a-zA-Z0-9]+$",
                message = "{validation.username}"
        )
        String username,

        @NotNull
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^*_+|<>?:{}])[A-Za-z\\d~!@#$%^*_+|<>?:{}]{4,}$",
                message = "{validation.password}")
        String password
) {
}
