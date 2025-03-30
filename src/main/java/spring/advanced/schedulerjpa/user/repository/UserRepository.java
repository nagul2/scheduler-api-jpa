package spring.advanced.schedulerjpa.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.advanced.schedulerjpa.user.domain.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 로그인 시 입력한 유저이름과 비밀번호로 찾기
     *
     * @param username 입력한 사용자 이름
     * @param password 입력한 비밀번호
     * @return 조회된 User
     */
    Optional<User> findByUsernameAndPassword(String username, String password);

    /**
     * DB에 동일한 유저가 있는지 확인
     *
     * @param username 사용자 이름
     * @return 등록 여부
     */
    boolean existsByUsername(String username);
}
