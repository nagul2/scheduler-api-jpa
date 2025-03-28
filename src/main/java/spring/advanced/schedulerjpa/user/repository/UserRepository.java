package spring.advanced.schedulerjpa.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.advanced.schedulerjpa.user.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
