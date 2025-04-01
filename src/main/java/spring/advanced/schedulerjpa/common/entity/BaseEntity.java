package spring.advanced.schedulerjpa.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 전체 Entity 에서 사용되는 공통 필드 - Auditing, 상속매핑 적용
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    /**
     * 생성일, 수정 불가적용
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createAt;

    /**
     * 수정일
     */
    @LastModifiedDate
    private LocalDateTime modifyAt;
}
