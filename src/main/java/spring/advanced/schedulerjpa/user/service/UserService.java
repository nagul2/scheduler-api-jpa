package spring.advanced.schedulerjpa.user.service;

import spring.advanced.schedulerjpa.user.domain.dto.UserCreateResponseDto;
import spring.advanced.schedulerjpa.user.domain.dto.UserFindResponseDto;
import spring.advanced.schedulerjpa.user.domain.dto.UserUpdateResponseDto;

import java.util.List;

public interface UserService {
    UserCreateResponseDto saveUser(String username, String email, String password);

    List<UserFindResponseDto> findAllUsers();

    UserFindResponseDto findUserById(Long id);

    UserUpdateResponseDto updateUser(Long id, String username, String email, String password, String validPassword);

    void deleteUser(Long id, String aLong);
}
