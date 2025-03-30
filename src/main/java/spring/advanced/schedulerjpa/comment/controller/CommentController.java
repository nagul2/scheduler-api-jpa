package spring.advanced.schedulerjpa.comment.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.advanced.schedulerjpa.auth.dto.AuthLoginResponseDto;
import spring.advanced.schedulerjpa.comment.domain.dto.CommentCommonRequestDto;
import spring.advanced.schedulerjpa.comment.domain.dto.CommentCommonResponseDto;
import spring.advanced.schedulerjpa.comment.domain.dto.CommentFindRequestDto;
import spring.advanced.schedulerjpa.comment.domain.dto.CommentFindResponseDto;
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
    public ResponseEntity<List<CommentFindResponseDto>> findAllCommentsByScheduleId(@RequestBody CommentFindRequestDto requestDto) {
        Long scheduleId = requestDto.scheduleId();
        return new ResponseEntity<>(commentService.findAllCommentsByScheduleId(scheduleId), HttpStatus.OK);
    }
}
