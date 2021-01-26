package com.rere.config;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*교차 출처 리소스 공유 문제 해결
다른 도메인에게 데이터를 요청하는 경우 보안상의 이유로 접근을 제한하는 경우를 제거
*/
@Configuration
public class CORSFilter implements Filter{

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException{
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods","POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Max-Age","3600");
        response.setHeader("Access-Control-Allow-Headers","X-Requested-With, Content-Type, "
                +"Authorization, Origin, Accept, Access-Control-Request-Method, Access-Control-Request-Headers");

        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig){}

    public void destroy() {}
}