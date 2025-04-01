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
@Transactional(readOnly = true) // 읽기 전용 트랜잭션 적용
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    /**
     * 댓글 저장 로직
     * 유저를 찾지 못하거나 일정을 차지 못하면 XXX_NOT_FOUND 예외 발생
     *
     * @param userId 작성자 id
     * @param scheduleId 댓글이 속한 일정의 id
     * @param content 저장할 내용
     * @return 생성된 댓글 id를 DTO로 반환
     */
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

    /**
     * 댓글 전체 조회 로직
     * 댓글을 찾지 못하면 COMMENT_NOT_FOUND 예외 발생
     *
     * @param scheduleId 댓글이 속한 일정의 id
     * @return 조회된 댓글을 DTO로 변환하여 반환
     */
    @Override
    public List<CommentFindResponseDto> findAllCommentsByScheduleId(Long scheduleId) {

        List<Comment> findCommentsByScheduleId = commentRepository.findAllByScheduleId(scheduleId);

        if (findCommentsByScheduleId.isEmpty()) {
            throw new NotFoundCommentException(ErrorCode.COMMENT_NOT_FOUND.getMessage());
        }

        return findCommentsByScheduleId.stream().map(CommentFindResponseDto::mapToDto).toList();
    }

    /**
     * 댓글 단건 조회 로직
     * 댓글을 찾지 못하면 COMMENT_NOT_FOUND 예외 발생
     *
     * @param id 조회할 댓글 id
     * @param scheduleId 댓글이 속한 일정 id
     * @return 조회된 댓글을 DTO로 변환하여 반환
     */
    @Override
    public CommentFindResponseDto findCommentByIdAndScheduleId(Long id, Long scheduleId) {
        Comment findCommentByIdAndScheduleId = findCommentByIdAndScheduleIdOElseThrow(id, scheduleId);
        return CommentFindResponseDto.mapToDto(findCommentByIdAndScheduleId);
    }

    /**
     * 댓글 수정 로직 - JPA 변경감지를 활용함
     * 댓글을 찾지 못하면 COMMENT_NOT_FOUND 예외 발생
     *
     * @param id 수정할 대상 댓글 id
     * @param scheduleId 댓글이 속한 일정 id
     * @param content 수정할 내용
     * @return 수정된 댓글의 id를 DTO에 담아서 반환
     */
    @Override
    @Transactional
    public CommentCommonResponseDto updateComment(Long id, Long scheduleId, String content) {
        Comment findCommentByIdAndScheduleId = findCommentByIdAndScheduleIdOElseThrow(id, scheduleId);
        findCommentByIdAndScheduleId.updateContent(content);
        return new CommentCommonResponseDto(findCommentByIdAndScheduleId.getId());
    }

    /**
     * 댓글 삭제 로직
     * 댓글을 찾지 못하면 COMMENT_NOT_FOUND 예외 발생
     *
     * @param id 삭제할 댓글 id
     * @param scheduleId 삭제할 댓글이 속한 일정 id
     */
    @Override
    @Transactional
    public void deleteComment(Long id, Long scheduleId) {
        Comment findCommentByIdAndScheduleId = findCommentByIdAndScheduleIdOElseThrow(id, scheduleId);
        commentRepository.delete(findCommentByIdAndScheduleId);
    }

    /**
     * 댓글 단건 조회, 수정, 삭제에서 공통으로 사용하는 댓글을 조회하고 조회 내용이 없으면 예외를 반환하는 메서드
     * 재사용을 위하여 메서드화하였음
     *
     * @param id 조회할 댓글 id
     * @param scheduleId 댓글이 속한 일정 id
     * @return 조회된 댓글 혹은 예외
     */
    private Comment findCommentByIdAndScheduleIdOElseThrow(Long id, Long scheduleId) {
        return commentRepository.findByIdAndScheduleId(id, scheduleId).orElseThrow(
                () -> new NotFoundCommentException(ErrorCode.COMMENT_NOT_FOUND.getMessage())
        );
    }
}
