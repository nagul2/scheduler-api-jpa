package spring.advanced.schedulerjpa.user.domain.dto;

public record UserUpdateRequestDto(
        String username,
        String email,
        String updatePassword,
        String validPassword
) {
}
