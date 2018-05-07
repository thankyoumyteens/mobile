package iloveyesterday.mobile.util;

import iloveyesterday.mobile.pojo.User;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public final class LoginUtil {

    /**
     * 获取登陆用户
     *
     * @return
     */
    public static User getCurrentUser(HttpServletRequest httpServletRequest) {
        String token = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        String userStr = RedisPoolUtil.get(token);
        return JsonUtil.string2Obj(userStr, User.class);
    }
}
