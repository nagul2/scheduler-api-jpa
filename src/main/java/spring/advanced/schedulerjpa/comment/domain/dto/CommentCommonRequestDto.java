package spring.advanced.schedulerjpa.comment.domain.dto;

public record CommentCommonRequestDto(
        Long scheduleId,
        String content
) {
}
