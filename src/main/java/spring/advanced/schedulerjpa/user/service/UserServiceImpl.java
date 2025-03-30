package spring.advanced.schedulerjpa.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.advanced.schedulerjpa.common.config.PasswordEncoder;
import spring.advanced.schedulerjpa.common.exception.*;
import spring.advanced.schedulerjpa.user.domain.dto.UserCreateResponseDto;
import spring.advanced.schedulerjpa.user.domain.dto.UserFindResponseDto;
import spring.advanced.schedulerjpa.user.domain.dto.UserUpdateResponseDto;
import spring.advanced.schedulerjpa.user.domain.entity.User;
import spring.advanced.schedulerjpa.user.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserCreateResponseDto saveUser(String username, String email, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new DuplicatedUsernameException(ErrorCode.DUPLICATED_USERNAME.getMessage());
        }

        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        try {
            User savedUser = userRepository.save(user);
            return new UserCreateResponseDto(savedUser.getId(), savedUser.getUsername());
        } catch (DataIntegrityViolationException e) {
            log.error("[강제 저장 시도 발생]");
            throw new DuplicatedUserException(ErrorCode.DUPLICATED_USER.getMessage());
        }
    }

    @Override
    public List<UserFindResponseDto> findAllUsers() {
        List<User> findAllUsers = userRepository.findAll();

        if (findAllUsers.isEmpty()) {
            throw new NotFoundCommentException(ErrorCode.USER_NOT_FOUND.getMessage());
        }

        return findAllUsers.stream().map(UserFindResponseDto::mapToDto).toList();
    }

    @Override
    public UserFindResponseDto findUserById(Long id) {
        User findUser = findUserOrElseThrow(id);
        return UserFindResponseDto.mapToDto(findUser);
    }

    @Override
    @Transactional
    public UserUpdateResponseDto updateUser(Long id, String username, String email, String password) {
        User findUser = findUserOrElseThrow(id);
        findUser.updateUser(username, email, password);

        return new UserUpdateResponseDto(id, findUser.getUsername(), findUser.getEmail());
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User findUser = findUserOrElseThrow(id);
        userRepository.delete(findUser);
    }

    /**
     * userRepository.findById(id).orElseThrow()로 못찾으면 에러를 던지고,
     * 찾으면 유저를 반환하는 코드를 메서드화하여 코드 중복을 해결하고 가독성있게 해결
     *
     * @param id 찾을 유저의 아이디
     * @return 찾으면 User, 못 찾으면 NotFoundUserException
     */
    private User findUserOrElseThrow(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundUserException(ErrorCode.USER_NOT_FOUND.getMessage())
        );
    }
}
