package spring.advanced.schedulerjpa.user.domain.dto;

import spring.advanced.schedulerjpa.user.domain.entity.User;

import java.time.LocalDateTime;

public record UserFindResponseDto(
        String username,
        String email,
        LocalDateTime modifyAt
) {

    static public UserFindResponseDto mapToDto(User user) {
        return new UserFindResponseDto(
                user.getUsername(),
                user.getEmail(),
                user.getModifyAt()
        );
    }

}
