package spring.advanced.schedulerjpa.common.config;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.advanced.schedulerjpa.auth.filter.LoginCheckFilter;

/**
 * 필터를 수동으로 스프링 빈으로 등록하기 위한 설정 클래스
 */
@Configuration
public class WebConfig {

    /**
     * 로그인 체크 필터를 스프링 빈으로 등록
     *
     * @return 필터 빈
     */
    @Bean
    public FilterRegistrationBean<Filter> loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/api/*");

        return filterRegistrationBean;
    }
}
