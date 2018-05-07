package iloveyesterday.mobile.common.interceptor;

import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseCode;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.util.CookieUtil;
import iloveyesterday.mobile.util.JsonUtil;
import iloveyesterday.mobile.util.RedisPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

/**
 * 登陆和权限验证
 */
@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        String methodName = handlerMethod.getMethod().getName();
        System.out.println(methodName);
        String className = handlerMethod.getBean().getClass().getSimpleName();

        StringBuilder requestParamBuffer = new StringBuilder();
        Map paramMap = request.getParameterMap();
        for (Object o : paramMap.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String mapKey = (String) entry.getKey();
            String mapValue = StringUtils.EMPTY;
            Object obj = entry.getValue();
            if (obj instanceof String[]) {
                String[] strs = (String[]) obj;
                mapValue = Arrays.toString(strs);
            }
            requestParamBuffer.append(mapKey).append("=").append(mapValue);
        }

        if (StringUtils.equals(className, "UserManageController") && StringUtils.equals(methodName, "login")) {
            log.info("interceptor login,className:{},methodName:{}", className, methodName);
            return true;
        }

        if (StringUtils.equals(methodName, "upload")) {
            log.info("interceptor upload,className:{},methodName:{}", className, methodName);
            return true;
        }

        log.info("interceptor,className:{},methodName:{},param:{}", className, methodName, requestParamBuffer.toString());

        User user = null;
        String token = CookieUtil.readLoginToken(request);
        if (!StringUtils.isBlank(token)) {
            String userStr = RedisPoolUtil.get(token);
            user = JsonUtil.string2Obj(userStr, User.class);
        }
//        User user = (User) request.getSession().getAttribute(Const.CURRENT_SELLER);

        if (user == null || (user.getRole() != Const.Role.SELLER)) {
//            response.reset(); // 会使filter返回的跨域信息失效
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");

            PrintWriter out = response.getWriter();

            if (user == null) {
                out.print(JsonUtil.obj2String(ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg())));
            } else {
                out.print(JsonUtil.obj2String(ResponseData.error("用户无权限操作")));
            }
            out.flush();
            out.close();

            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
