package spring.advanced.schedulerjpa.schedule.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import spring.advanced.schedulerjpa.schedule.domain.entity.Schedule;

import java.time.LocalDateTime;

/**
 * 전체 조회 응답 DTO
 *
 * @param id 일정 id
 * @param username 작성자 이름(로그인 아이디)
 * @param title 일정 제목
 * @param content 일정 내용
 * @param createAt 생성일 - JsonFormat 적용
 * @param modifyAt 수정일 - JsonFormat 적용
 * @param commentCount 일정에 달린 댓글 갯수
 */
public record ScheduleFindAllPagingResponseDto(
        Long id,
        String username,
        String title,
        String content,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime modifyAt,
        Long commentCount
        ) {
}
