package spring.advanced.schedulerjpa.auth.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AuthLoginRequestDto(
        /**
         * NotNull 제약 조건 적용
         * 영어 대소문자, 숫자만 허용
         */
        @NotNull
        @Pattern(regexp = "^[a-zA-Z0-9]+$",
                message = "{validation.username}"
        )
        String username,

        /**
         * NotNull 제약 조건 적용
         * 영어 대소분자 숫자, 특수문자 허용 및 4글자 이상 영어 + 숫자 + 특수문자 조합 필수 적용
         */
        @NotNull
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^*_+|<>?:{}])[A-Za-z\\d~!@#$%^*_+|<>?:{}]{4,}$",
                message = "{validation.password}")
        String password
) {
}
