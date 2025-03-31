package spring.advanced.schedulerjpa.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.advanced.schedulerjpa.auth.dto.AuthLoginResponseDto;
import spring.advanced.schedulerjpa.common.config.PasswordEncoder;
import spring.advanced.schedulerjpa.common.exception.AuthFailedException;
import spring.advanced.schedulerjpa.common.exception.ErrorCode;
import spring.advanced.schedulerjpa.user.domain.entity.User;
import spring.advanced.schedulerjpa.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인
     * passwordEncoder.matches()를 통해 비밀번호 검증 로직 추가
     *
     * @param username 요청 username
     * @param password 요청 password
     * @return 못찾으면 AuthFiledException, 찾으면 응답 DTO
     */
    public AuthLoginResponseDto login(String username, String password) {

        User findUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthFailedException(ErrorCode.LOGIN_FAILED.getMessage())
        );

        if (!passwordEncoder.matches(password, findUser.getPassword())) {
            throw new AuthFailedException(ErrorCode.LOGIN_FAILED_PASSWORD.getMessage());
        }

        return new AuthLoginResponseDto(findUser.getId(), findUser.getUsername());
    }
}
