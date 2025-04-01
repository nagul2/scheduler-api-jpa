package spring.advanced.schedulerjpa.common.constant;

/**
 * 인증관련 상수를 관리
 */
public abstract class AuthConst {

    /**
     * 로그인된 유저를 등록하는 세션 키값
     */
    public static final String LOGIN_MEMBER = "loginMember";

    /**
     * 필터의 검증을 거치지 않아도 되는 URL 리스트
     */
    public static final String[] WHITE_LIST = {"/api/users/signup", "/api/login", "/api/logout"};

}
