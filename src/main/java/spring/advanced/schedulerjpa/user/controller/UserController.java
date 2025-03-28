package spring.advanced.schedulerjpa.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.advanced.schedulerjpa.user.domain.dto.UserCommonRequestDto;
import spring.advanced.schedulerjpa.user.domain.dto.UserCreateResponseDto;
import spring.advanced.schedulerjpa.user.domain.dto.UserFindResponseDto;
import spring.advanced.schedulerjpa.user.domain.dto.UserUpdateResponseDto;
import spring.advanced.schedulerjpa.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserCreateResponseDto> addUser(@RequestBody UserCommonRequestDto requestDto) {
        String username = requestDto.username();
        String email = requestDto.email();
        return new ResponseEntity<>(userService.saveUser(username, email), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserFindResponseDto>> findUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserFindResponseDto> findUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserUpdateResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserCommonRequestDto requestDto) {

        String username = requestDto.username();
        String email = requestDto.email();

        return new ResponseEntity<>(userService.updateUser(id, username, email), HttpStatus.OK);
    }

}
