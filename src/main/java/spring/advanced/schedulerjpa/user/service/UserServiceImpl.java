package spring.advanced.schedulerjpa.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.advanced.schedulerjpa.user.domain.dto.UserCreateResponseDto;
import spring.advanced.schedulerjpa.user.domain.dto.UserFindResponseDto;
import spring.advanced.schedulerjpa.user.domain.dto.UserUpdateResponseDto;
import spring.advanced.schedulerjpa.user.domain.entity.User;
import spring.advanced.schedulerjpa.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserCreateResponseDto saveUser(String username, String email, String password) {

        User user = User.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();

        User savedUser = userRepository.save(user);
        return new UserCreateResponseDto(savedUser.getId(), savedUser.getUsername());
    }

    @Override
    public List<UserFindResponseDto> findAllUsers() {
        List<User> findAllUsers = userRepository.findAll();
        return findAllUsers.stream().map(UserFindResponseDto::mapToDto).toList();
    }

    @Override
    public UserFindResponseDto findUserById(Long id) {
        // 필수 구현 단계에서는 예외 처리 X, 도전 때 구현
        User findUser = userRepository.findById(id).orElse(null);

        if (findUser == null) {
            return null;
        }
        return UserFindResponseDto.mapToDto(findUser);
    }

    @Override
    @Transactional
    public UserUpdateResponseDto updateUser(Long id, String username, String email, String password) {
        // 필수 구현 단계에서는 예외 처리 X, 도전 때 구현
        User findUser = userRepository.findById(id).orElse(null);

        if (findUser == null) {
            return null;
        }

        findUser.updateUser(username, email, password);
        return new UserUpdateResponseDto(id, findUser.getUsername(), findUser.getEmail());
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        // 필수 구현 단계에서는 예외 처리 X, 도전 때 구현
        User findUser = userRepository.findById(id).orElse(null);

        if (findUser == null) {
            return;
        }

        userRepository.delete(findUser);
    }
}
