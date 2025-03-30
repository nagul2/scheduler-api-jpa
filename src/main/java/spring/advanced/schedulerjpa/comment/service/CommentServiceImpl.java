package spring.advanced.schedulerjpa.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.advanced.schedulerjpa.comment.domain.dto.CommentCommonResponseDto;
import spring.advanced.schedulerjpa.comment.domain.dto.CommentFindResponseDto;
import spring.advanced.schedulerjpa.comment.domain.entity.Comment;
import spring.advanced.schedulerjpa.comment.repository.CommentRepository;
import spring.advanced.schedulerjpa.common.exception.ErrorCode;
import spring.advanced.schedulerjpa.common.exception.NotFoundCommentException;
import spring.advanced.schedulerjpa.common.exception.NotFoundScheduleException;
import spring.advanced.schedulerjpa.common.exception.NotFoundUserException;
import spring.advanced.schedulerjpa.schedule.domain.entity.Schedule;
import spring.advanced.schedulerjpa.schedule.repository.ScheduleRepository;
import spring.advanced.schedulerjpa.user.domain.entity.User;
import spring.advanced.schedulerjpa.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    @Transactional
    public CommentCommonResponseDto saveComment(Long userId, Long scheduleId, String content) {

        User findUser = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundUserException(ErrorCode.USER_NOT_FOUND.getMessage())
        );

        Schedule findSchedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new NotFoundScheduleException(ErrorCode.SCHEDULE_NOT_FOUND.getMessage())
        );

        Comment comment = Comment.builder()
                .schedule(findSchedule)
                .user(findUser)
                .content(content)
                .build();


        Comment savedComment = commentRepository.save(comment);
        return new CommentCommonResponseDto(savedComment.getId());
    }

    @Override
    public List<CommentFindResponseDto> findAllCommentsByScheduleId(Long scheduleId) {

        List<Comment> findCommentsByScheduleId = commentRepository.findAllByScheduleId(scheduleId);

        if (findCommentsByScheduleId.isEmpty()) {
            throw new NotFoundCommentException(ErrorCode.COMMENT_NOT_FOUND.getMessage());
        }

        return findCommentsByScheduleId.stream().map(CommentFindResponseDto::mapToDto).toList();
    }

    @Override
    public CommentFindResponseDto findCommentByIdAndScheduleId(Long id, Long scheduleId) {
        Comment findCommentByIdAndScheduleId = commentRepository.findByIdAndScheduleId(id, scheduleId).orElseThrow(
                () -> new NotFoundCommentException(ErrorCode.COMMENT_NOT_FOUND.getMessage())
        );
        return CommentFindResponseDto.mapToDto(findCommentByIdAndScheduleId);
    }
}
