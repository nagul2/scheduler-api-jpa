package spring.advanced.schedulerjpa.user.domain.dto;

public record UserUpdateResponseDto(
        Long id,
        String username,
        String email) {
}
