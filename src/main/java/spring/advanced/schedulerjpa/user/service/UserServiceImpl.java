package spring.advanced.schedulerjpa.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.advanced.schedulerjpa.auth.dto.AuthLoginResponseDto;
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

    /**
     * 유저 저장 로직
     * 이미 등록되어있으면 DUPLICATED_USERNAME, DUPLICATED_USER 예외 발생
     * 비밀번호 저장시 암호화 적용
     *
     * @param username 로그인 가능한 유저 아이디
     * @param email 유저 이메일
     * @param password 유저 비밀번호
     * @return 저장 성공한 id와 유저 아이디이름을 DTO에 담아 반환
     */
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

    /**
     * 유저 전체 조회 로직
     * 조회된 대상이 없다면 USER_NOT_FOUND 예외 발생
     *
     * @return 조회된 유저의 필요한 정보만 DTO에 담아서 리스트로 반환
     */
    @Override
    public List<UserFindResponseDto> findAllUsers() {
        List<User> findAllUsers = userRepository.findAll();

        if (findAllUsers.isEmpty()) {
            throw new NotFoundUserException(ErrorCode.USER_NOT_FOUND.getMessage());
        }

        return findAllUsers.stream().map(UserFindResponseDto::mapToDto).toList();
    }

    /**
     * 유저 단건 조회
     * 조회된 대상이 없다면 USER_NOT_FOUND 예외 발생
     *
     * @param id 조회할 유저 아이디
     * @return 조회된 유저의 필요한 정보만 DTO에 담아서 반환
     */
    @Override
    public UserFindResponseDto findUserById(Long id) {
        User findUser = findUserOrElseThrow(id);
        return UserFindResponseDto.mapToDto(findUser);
    }

    /**
     * 유저 수정 로직
     * 수정할 유저를 못찾으면 USER_NOT_FOUND 예외 발생
     * 수정할 접근 권한이 없으면 FORBIDDEN 예외 발생
     * 비밀번호 수정시 암호화 하여 수정
     *
     * @param id 수정 대상 유저의 id
     * @param username 수정할 유저 이름
     * @param email 수정할 유저 email
     * @param password 수정할 비밀반호
     * @param loginDto 로그인한 유저 정보
     * @return 수정된 유저와 유저이름, 이메일 정보를 DTO에 담아서 반환
     */
    @Override
    @Transactional
    public UserUpdateResponseDto updateUser(Long id, String username, String email, String password, AuthLoginResponseDto loginDto) {
        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            throw new DuplicatedUserException(ErrorCode.DUPLICATED_USER.getMessage());
        }

        User findUser = findUserOrElseThrow(id);
        validUser(loginDto, findUser);
        findUser.updateUser(username, email, passwordEncoder.encode(password));

        return new UserUpdateResponseDto(id, findUser.getUsername(), findUser.getEmail());
    }

    /**
     * 유저 삭제 로직
     * 삭제할 유저를 못찾으면 USER_NOT_FOUND 예외 발생
     * 삭제할 접근 권한이 없으면 FORBIDDEN 예외 발생
     *
     * @param id
     * @param loginDto
     */
    @Override
    @Transactional
    public void deleteUser(Long id, AuthLoginResponseDto loginDto) {
        User findUser = findUserOrElseThrow(id);

        validUser(loginDto, findUser);
        userRepository.delete(findUser);
    }

    /**
     * 본인의 정보만 수정하거나 삭제할 수 있도록 검증하는 로직을 재사용할 수 있도록 메서드ghk
     *
     * @param loginDto 세션에서 꺼낸 로그인 사용자 정보
     * @param findUser 수정할 대상 User
     */
    private void validUser(AuthLoginResponseDto loginDto, User findUser) {
        if (!findUser.getUsername().equals(loginDto.username())) {
            throw new AuthFailedException(ErrorCode.FORBIDDEN, ErrorCode.FORBIDDEN.getMessage());
        }
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
