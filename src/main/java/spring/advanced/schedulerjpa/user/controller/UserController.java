package spring.advanced.schedulerjpa.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.advanced.schedulerjpa.auth.dto.AuthLoginResponseDto;
import spring.advanced.schedulerjpa.common.constant.AuthConst;
import spring.advanced.schedulerjpa.user.domain.dto.*;
import spring.advanced.schedulerjpa.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 유저 저장 API
     *
     * @param requestDto 저장할 유저 정보
     * @return 저장한 유저 정보가 담긴 DTO, 성공 시 201 응답
     */
    @PostMapping("/signup")
    public ResponseEntity<UserCreateResponseDto> addUser(@Valid @RequestBody UserCreateRequestDto requestDto) {
        String username = requestDto.username();
        String email = requestDto.email();
        String password = requestDto.password();
        return new ResponseEntity<>(userService.saveUser(username, email, password), HttpStatus.CREATED);
    }

    /**
     * 유저 전체 조회 API
     *
     * @return DTO로 변환된 조회된 유저를 리스트로 반환, 성공시 200 응답
     */
    @GetMapping
    public ResponseEntity<List<UserFindResponseDto>> findUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    /**
     * 유저 단건 조회 API
     *
     * @param id 조회할 유저 id
     * @return 조회된 유저 정보를 DTO로 반환
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserFindResponseDto> findUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    /**
     * 유저 수정 API
     *
     * @param id 수정할 유저 id
     * @param requestDto 수정할 유저 내용
     * @param servletRequest 서블릿 리퀘스트(로그인 정보 꺼내기 위함)
     * @return 변경된 유저 id가 남긴 DTO, 성공시 200 응답
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserUpdateResponseDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequestDto requestDto,
            HttpServletRequest servletRequest) {

        AuthLoginResponseDto loginDto = (AuthLoginResponseDto) servletRequest.getSession().getAttribute(AuthConst.LOGIN_MEMBER);

        String username = requestDto.username();
        String email = requestDto.email();
        String password = requestDto.password();

        return new ResponseEntity<>(userService.updateUser(id, username, email, password, loginDto), HttpStatus.OK);
    }

    /**
     * 유저 삭제 API
     *
     * @param id 삭제할 유저 id
     * @param servletRequest 서블릿 리퀘스트(로그인 정보 꺼내기 위함)
     * @return 성공시 200 상태 코드
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, HttpServletRequest servletRequest) {
        AuthLoginResponseDto loginDto = (AuthLoginResponseDto) servletRequest.getSession().getAttribute(AuthConst.LOGIN_MEMBER);

        userService.deleteUser(id, loginDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
