package spring.advanced.schedulerjpa.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import spring.advanced.schedulerjpa.user.domain.entity.User;

import java.time.LocalDateTime;

public record UserFindResponseDto(
        String username,
        String email,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
