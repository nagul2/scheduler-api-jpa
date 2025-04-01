package spring.advanced.schedulerjpa.user.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.advanced.schedulerjpa.common.entity.BaseEntity;

@Builder
@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    /**
     * PK, DB 자동 생성 전략 적용
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 사용자 아이디, unique, not null 제약 조건 적용
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * 사용자 이메일, unique 제약 조건 적용
     */
    @Column(unique = true)
    private String email;

    /**
     * 사용자 비밀번호
     */
    private String password;

    /**
     * user 수정 메서드
     *
     * @param username 수정할 유저 id
     * @param email 수정할 email
     * @param password 수정할 비밀번호
     */
    public void updateUser(String username, String email, String password) {
        if (username != null) {
            this.username = username;
        }

        if (email != null) {
            this.email = email;
        }

        if (password != null) {
            this.password = password;
        }
    }

}
