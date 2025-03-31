package spring.advanced.schedulerjpa.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.advanced.schedulerjpa.auth.dto.AuthLoginRequestDto;
import spring.advanced.schedulerjpa.auth.dto.AuthLoginResponseDto;
import spring.advanced.schedulerjpa.auth.service.LoginService;
import spring.advanced.schedulerjpa.common.consant.AuthConst;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;

    /**
     * 로그인 API
     *
     * @param requestDto 요그인 요청 값
     * @param servletRequest servletRequest
     * @return login 성공한 User의 id, userName
     */
    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponseDto> login(@RequestBody AuthLoginRequestDto requestDto, HttpServletRequest servletRequest) {
        AuthLoginResponseDto loginUserDto = loginService.login(requestDto.username(), requestDto.password());
        HttpSession session = servletRequest.getSession();
        session.setAttribute(AuthConst.LOGIN_MEMBER, loginUserDto);
        return new ResponseEntity<>(loginUserDto, HttpStatus.OK);
    }

    /**
     * 로그아웃 API
     *
     * @param servletRequest servletRequest
     * @return HTTP 상태 코드
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest servletRequest) {

        HttpSession session = servletRequest.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
