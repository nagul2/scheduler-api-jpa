package spring.advanced.schedulerjpa.schedule.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import spring.advanced.schedulerjpa.schedule.domain.entity.Schedule;

import java.time.LocalDateTime;

/**
 * 일정 단건 조회
 *
 * @param id 일정 id
 * @param username 작성자 이름(로그인 아이디)
 * @param title 일정 제목
 * @param content 일정 내용
 * @param modifyAt 수정일 - JsonFormat 적용
 */
public record ScheduleFindResponseDto(
        Long id,
        String username,
        String title,
        String content,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime modifyAt
        ) {

    /**
     * 일정을 DTO로 변환하여 생성하는 정적 팩토리 메서드 작성
     *
     * @param schedule 조회된 일정
     * @return 변환한 일정 DTO
     */
    static public ScheduleFindResponseDto mapToDto(Schedule schedule) {
        return new ScheduleFindResponseDto(
                schedule.getId(),
                schedule.getUser().getUsername(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getModifyAt()
        );
    }

}
