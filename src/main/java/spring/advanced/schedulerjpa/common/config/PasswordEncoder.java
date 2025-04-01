package spring.advanced.schedulerjpa.common.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * 비밀번호 암호화 라이브러리를 사용하기 위한 클래스
 * 스프링 시큐리티의 PasswordEncoder 와 비슷한 기능을 하여 이름을 동일하게 지었음
 * 의존관계 자동 주입을 적용하기 위해 @Component 를 적용함
 */
@Component
public class PasswordEncoder {

    /**
     * 라이브러리를 사용하여 비밀번호를 암호화
     *
     * @param rawPassword 암호화할 비밀번호
     * @return 암호화된 해시 문자열
     */
    public String encode(String rawPassword) {
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    /**
     * 입력된 암호화 DB에 저장된 해싱 문자열을 비교하는 메서드
     *
     * @param rawPassword 입력된 비밀번호
     * @param encodedPassword 암호화된 해싱 문자열
     * @return 매칭 여부
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }
}
