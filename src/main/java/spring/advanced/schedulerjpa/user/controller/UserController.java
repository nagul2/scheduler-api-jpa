package spring.advanced.schedulerjpa.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.advanced.schedulerjpa.user.domain.dto.UserCreateRequestDto;
import spring.advanced.schedulerjpa.user.domain.dto.UserCreateResponseDto;
import spring.advanced.schedulerjpa.user.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserCreateResponseDto> addUser(@RequestBody UserCreateRequestDto requestDto) {
        String username = requestDto.username();
        String email = requestDto.email();
        return new ResponseEntity<>(userService.saveUser(username, email), HttpStatus.CREATED);
    }
}
