package spring.advanced.schedulerjpa.comment.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.advanced.schedulerjpa.auth.dto.AuthLoginResponseDto;
import spring.advanced.schedulerjpa.comment.domain.dto.*;
import spring.advanced.schedulerjpa.comment.service.CommentService;
import spring.advanced.schedulerjpa.common.consant.AuthConst;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

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

    @GetMapping
    public ResponseEntity<List<CommentFindResponseDto>> findAllCommentsByScheduleId(@RequestParam Long scheduleId) {
        return new ResponseEntity<>(commentService.findAllCommentsByScheduleId(scheduleId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentFindResponseDto> findCommentByIdAndScheduleId(
            @PathVariable Long id,
            @RequestParam Long scheduleId) {

        return new ResponseEntity<>(commentService.findCommentByIdAndScheduleId(id, scheduleId), HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentCommonResponseDto> updateComment(
            @PathVariable Long id,
            @RequestBody CommentCommonRequestDto requestDto) {

        Long scheduleId = requestDto.scheduleId();
        String content = requestDto.content();

        return new ResponseEntity<>(commentService.updateComment(id, scheduleId, content), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id,  @RequestParam Long scheduleId) {
        commentService.deleteComment(id, scheduleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
