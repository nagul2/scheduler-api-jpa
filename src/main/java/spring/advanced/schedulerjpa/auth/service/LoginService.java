package spring.advanced.schedulerjpa.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.advanced.schedulerjpa.auth.dto.AuthLoginResponseDto;
import spring.advanced.schedulerjpa.common.exception.AuthFailedException;
import spring.advanced.schedulerjpa.common.exception.ErrorCode;
import spring.advanced.schedulerjpa.user.domain.entity.User;
import spring.advanced.schedulerjpa.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public AuthLoginResponseDto login(String username, String password) {

        // 모든 예외처리는 도전에서 수행
        User findUser = userRepository.findByUsernameAndPassword(username, password).orElse(null);

        if (findUser == null) {
            throw new AuthFailedException(ErrorCode.LOGIN_FAILED.getMessage());
        }

        return new AuthLoginResponseDto(findUser.getId(),findUser.getUsername());
    }
}
