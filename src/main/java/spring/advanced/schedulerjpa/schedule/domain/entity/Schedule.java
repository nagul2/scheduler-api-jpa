package spring.advanced.schedulerjpa.schedule.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.advanced.schedulerjpa.common.entity.BaseEntity;
import spring.advanced.schedulerjpa.schedule.domain.dto.ScheduleUpdateRequestDto;

@Table(name = "schedule")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String title;

    @Lob
    private String content;

    public void updateSchedule(ScheduleUpdateRequestDto requestDto) {
        if (requestDto.title() != null) {
            title = requestDto.title();
        }
        if (requestDto.content() != null) {
            content = requestDto.content();
        }
    }
}
