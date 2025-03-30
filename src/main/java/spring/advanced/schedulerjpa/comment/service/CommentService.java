package spring.advanced.schedulerjpa.comment.service;

import spring.advanced.schedulerjpa.comment.domain.dto.CommentCommonResponseDto;
import spring.advanced.schedulerjpa.comment.domain.dto.CommentFindResponseDto;

import java.util.List;

public interface CommentService {
    CommentCommonResponseDto saveComment(Long userId, Long scheduleId, String content);

    List<CommentFindResponseDto> findAllCommentsByScheduleId(Long scheduleId);

    CommentFindResponseDto findCommentByIdAndScheduleId(Long id, Long scheduleId);

    CommentCommonResponseDto updateComment(Long id, Long scheduleId, String content);
}
