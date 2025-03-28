package spring.advanced.schedulerjpa.user.domain.dto;

public record UserCreateRequestDto(
        String username,
        String email
) {
}
