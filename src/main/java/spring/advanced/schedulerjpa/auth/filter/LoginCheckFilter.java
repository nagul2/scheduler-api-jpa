package spring.advanced.schedulerjpa.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import spring.advanced.schedulerjpa.common.constant.AuthConst;
import spring.advanced.schedulerjpa.common.exception.ErrorCode;
import spring.advanced.schedulerjpa.common.exception.ErrorDto;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@Slf4j
public class LoginCheckFilter extends OncePerRequestFilter {

    /**
     * 로그인 인증 필터
     *
     * @param request 클라이언트 요청
     * @param response 클라이언트 응답
     * @param filterChain 필터 체인
     * @throws ServletException 서블릿 예외
     * @throws IOException IO 예외
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // Filter를 통과하지 못하면 401 응답 작성
        if (loginCheckPath(requestURI)) {
            log.error("[login Failed] {}", requestURI);
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute(AuthConst.LOGIN_MEMBER) == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);    // 응답코드 401
                response.setContentType("application/json");    // 콘텐츠 타입 설정
                response.setCharacterEncoding("UTF-8");         // 인코딩 설정

                ErrorDto errorDto = new ErrorDto(
                        ErrorCode.UNAUTHORIZED_ACCESS.getCode(),
                        ErrorCode.UNAUTHORIZED_ACCESS,
                        ErrorCode.UNAUTHORIZED_ACCESS.getMessage(),
                        LocalDateTime.now()
                );
                String json = new ObjectMapper()
                        .registerModule(new JavaTimeModule())   // LocalDateTime 직렬화를 위한 모듈 등록
                        .disable(WRITE_DATES_AS_TIMESTAMPS)     // timestamp -> 문자열로 처리
                        .writeValueAsString(errorDto);          // 객체 -> JSON 문자열로 변경
                response.getWriter().write(json);               // 클라이언트에 JSON 응답을 전송
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * WHITE_LIST 매칭 메서드
     *
     * @param requestURI 요청 URI
     * @return 매칭 여부, WHITE 리스트가 아니면 true -> 예외 처리 로직으로 넘어감
     */
    private boolean loginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(AuthConst.WHITE_LIST, requestURI);
    }
}
