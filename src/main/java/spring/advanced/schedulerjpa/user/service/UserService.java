package spring.advanced.schedulerjpa.user.service;

import spring.advanced.schedulerjpa.user.domain.dto.UserCreateResponseDto;
import spring.advanced.schedulerjpa.user.domain.dto.UserFindResponseDto;

import java.util.List;

public interface UserService {
    UserCreateResponseDto saveUser(String username, String email);

    List<UserFindResponseDto> findAllUsers();

    UserFindResponseDto findUserById(Long id);
}
