package iloveyesterday.mobile.filter; /**
 * Created by Ricky on 2017/3/14.
 */

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * 跨域
 */
public class CORSFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String originHeader = request.getHeader("Origin");
        String[] IPs = {"http://localhost:8080", "http://localhost:8081"};
        if (Arrays.asList(IPs).contains(originHeader)) {
            // 允许的域名
            response.setHeader("Access-Control-Allow-Origin", originHeader);
//            response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
            // 允许的请求类型，多个用逗号隔开
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            // 预请求缓存时间，单位秒
            response.setHeader("Access-Control-Max-Age", "3600");
            // 在实际请求中，允许的自定义header，多个用逗号隔开
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type");
            // 是否允许带凭证的请求
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

}