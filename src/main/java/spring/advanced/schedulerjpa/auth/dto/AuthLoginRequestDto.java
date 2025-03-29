package spring.advanced.schedulerjpa.auth.dto;

public record AuthLoginRequestDto(
        String username,
        String password
) {
}
