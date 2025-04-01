package spring.advanced.schedulerjpa.comment.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.advanced.schedulerjpa.auth.dto.AuthLoginResponseDto;
import spring.advanced.schedulerjpa.comment.domain.dto.*;
import spring.advanced.schedulerjpa.comment.service.CommentService;
import spring.advanced.schedulerjpa.common.constant.AuthConst;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 생성 API
     *
     * @param requestDto 댓글 생성 요청값
     * @param request 서블릿 요청 (세션 정보 꺼내기 위함)
     * @return 생성된 댓글 정보, 성공시 201 반환
     */
    @PostMapping
    public ResponseEntity<CommentCommonResponseDto> addComment(
            @RequestBody CommentCommonRequestDto requestDto,
            HttpServletRequest request) {

        AuthLoginResponseDto loginDto = (AuthLoginResponseDto) request.getSession().getAttribute(AuthConst.LOGIN_MEMBER);
        Long userId = loginDto.id();
        Long scheduleId = requestDto.scheduleId();
        String content = requestDto.content();

        return new ResponseEntity<>(commentService.saveComment(userId, scheduleId, content), HttpStatus.CREATED);
    }

    /**
     * 일정에 포함된 댓글 전체 조회 API
     *
     * @param scheduleId 일정 id
     * @return 전체 조회된 일정의 댓글 List, 성공시 200 응답
     */
    @GetMapping
    public ResponseEntity<List<CommentFindResponseDto>> findAllCommentsByScheduleId(@RequestParam Long scheduleId) {
        return new ResponseEntity<>(commentService.findAllCommentsByScheduleId(scheduleId), HttpStatus.OK);
    }

    /**
     * 특정 일정의 댓글 단건 조회
     *
     * @param id 조회할 댓글 id
     * @param scheduleId 댓글이 포함된 일정 id
     * @return 조회된 댓글, 성공시 200 응답
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommentFindResponseDto> findCommentByIdAndScheduleId(
            @PathVariable Long id,
            @RequestParam Long scheduleId) {

        return new ResponseEntity<>(commentService.findCommentByIdAndScheduleId(id, scheduleId), HttpStatus.OK);

    }

    /**
     * 댓글 수정 API
     *
     * @param id 수정할 댓글의 id
     * @param requestDto 수정할 댓글 내용
     * @return 수정된 댓글 정보, 성공시 200 응답
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommentCommonResponseDto> updateComment(
            @PathVariable Long id,
            @RequestBody CommentCommonRequestDto requestDto) {

        Long scheduleId = requestDto.scheduleId();
        String content = requestDto.content();

        return new ResponseEntity<>(commentService.updateComment(id, scheduleId, content), HttpStatus.OK);
    }

    /**
     * 댓글 삭제 API
     *
     * @param id 삭제할 댓글 id
     * @param scheduleId 댓글이 포함된 일정의 id
     * @return 성공시 200 응답
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id,  @RequestParam Long scheduleId) {
        commentService.deleteComment(id, scheduleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
