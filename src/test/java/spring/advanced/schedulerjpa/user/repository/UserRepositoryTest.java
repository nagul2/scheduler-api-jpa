package spring.advanced.schedulerjpa.user.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import spring.advanced.schedulerjpa.user.domain.entity.User;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    public static final User TEST_USER = User.builder().
            username("name")
            .email("email@email.com")
            .password("qwer1234!@#$")
            .build();

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveTest() {

        // given
        User user = TEST_USER;

        // when
        User savedUser = userRepository.save(user);

        // then
        Assertions.assertNotNull(savedUser);

    }

}