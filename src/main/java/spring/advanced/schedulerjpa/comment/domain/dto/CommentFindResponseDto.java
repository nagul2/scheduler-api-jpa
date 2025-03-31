package spring.advanced.schedulerjpa.comment.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import spring.advanced.schedulerjpa.comment.domain.entity.Comment;

import java.time.LocalDateTime;

public record CommentFindResponseDto(
        Long id,
        Long scheduleId,
        String username,
        String content,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime ModifyAt
) {

    static public CommentFindResponseDto mapToDto(Comment comment) {
        return new CommentFindResponseDto(
                comment.getId(),
                comment.getSchedule().getId(),
                comment.getUser().getUsername(),
                comment.getContent(),
                comment.getModifyAt()
        );
    }
}
