package spring.advanced.schedulerjpa.comment.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.advanced.schedulerjpa.common.entity.BaseEntity;
import spring.advanced.schedulerjpa.schedule.domain.entity.Schedule;
import spring.advanced.schedulerjpa.user.domain.entity.User;

@Table(name = "comment")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {

    /**
     * PK, DB 자동 생성 전략 적용
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 일정과 다대일 연관관계 매핑, 지연로딩 적용
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    /**
     * 유저와 다대일 연관관계 매핑, 지연로딩 적용
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 일정 내용, not null 적용
     */
    @Column(nullable = false)
    private String content;

    /**
     * 댓글 수정 메서드(JPA 변경감지 이용)
     *
     * @param content 수정할 댓글 내용
     */
    public void updateContent(String content) {
        if (content != null) {
            this.content = content;
        }
    }
}
