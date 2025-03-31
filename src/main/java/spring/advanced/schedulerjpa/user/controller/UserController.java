package spring.advanced.schedulerjpa.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.advanced.schedulerjpa.user.domain.dto.*;
import spring.advanced.schedulerjpa.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserCreateResponseDto> addUser(@Valid @RequestBody UserCreateRequestDto requestDto) {
        String username = requestDto.username();
        String email = requestDto.email();
        String password = requestDto.password();
        return new ResponseEntity<>(userService.saveUser(username, email, password), HttpStatus.CREATED);
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
            @Valid @RequestBody UserUpdateRequestDto requestDto) {

        String username = requestDto.username();
        String email = requestDto.email();
        String password = requestDto.password();

        return new ResponseEntity<>(userService.updateUser(id, username, email, password), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
