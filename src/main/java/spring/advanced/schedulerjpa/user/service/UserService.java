package spring.advanced.schedulerjpa.user.service;

import spring.advanced.schedulerjpa.user.domain.dto.UserCreateResponseDto;

public interface UserService {
    UserCreateResponseDto saveUser(String username, String email);
}
