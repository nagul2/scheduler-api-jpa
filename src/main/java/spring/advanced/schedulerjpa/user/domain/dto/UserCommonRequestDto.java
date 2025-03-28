package spring.advanced.schedulerjpa.user.domain.dto;

public record UserCommonRequestDto(
        String username,
        String email
) {
}
