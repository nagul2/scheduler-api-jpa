package spring.advanced.schedulerjpa.comment.service;

import spring.advanced.schedulerjpa.comment.domain.dto.CommentCommonResponseDto;

public interface CommentService {
    CommentCommonResponseDto saveComment(Long userId, Long scheduleId, String content);
}
