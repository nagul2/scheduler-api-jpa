package spring.advanced.schedulerjpa.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.advanced.schedulerjpa.user.domain.dto.UserCreateResponseDto;
import spring.advanced.schedulerjpa.user.domain.entity.User;
import spring.advanced.schedulerjpa.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserCreateResponseDto saveUser(String username, String email) {

        User user = User.builder()
                .username(username)
                .email(email)
                .build();

        User savedUser = userRepository.save(user);
        return new UserCreateResponseDto(savedUser.getId(), savedUser.getUsername());
    }
}
