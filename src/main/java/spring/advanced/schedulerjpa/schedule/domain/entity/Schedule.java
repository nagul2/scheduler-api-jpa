package spring.advanced.schedulerjpa.schedule.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.advanced.schedulerjpa.common.entity.BaseEntity;
import spring.advanced.schedulerjpa.user.domain.entity.User;

@Table(name = "schedule")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule extends BaseEntity {

    /**
     * PK, DB 자동 생성 전략 적용
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 유저와 다대일 연관관계 매핑, 지연로딩 적용
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 일정 제목, not null 적용
     */
    @Column(nullable = false)
    private String title;

    /**
     * 일정 내용, 길이 제한이 없는 @Lob 애노테이션 매핑
     */
    @Lob
    private String content;

    /**
     * 일정 수정 메서드
     *
     * @param title 수정할 일정 제목
     * @param content 수정할 일정 내용
     */
    public void updateSchedule(String title, String content) {
        if (title != null) {
            this.title = title;
        }
        if (content != null) {
            this.content = content;
        }
    }
}
