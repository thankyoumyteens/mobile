package iloveyesterday.mobile.util;

import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.pojo.User;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public final class LoginUtil {

    /**
     * 保存当前登陆的用户
     *
     * @param session
     * @param response
     * @param user
     */
    public static void saveCurrentUser(HttpSession session, HttpServletResponse response, User user) {
        saveCurrentUserSession(session, user);
//        saveCurrentUserRedis(session, response, user);
    }

    /**
     * 获取当前登陆的用户
     *
     * @param request
     * @return
     */
    public static User getCurrentUser(HttpServletRequest request) {
        return getCurrentUserSession(request);
//        return getCurrentUserRedis(request);
    }

    /**
     * 删除当前登陆的用户
     *
     * @param request
     * @param response
     */
    public static void deleteCurrentUser(HttpServletRequest request, HttpServletResponse response) {
        deleteCurrentUserSession(request);
//        deleteCurrentUserRedis(request, response);
    }

    /**
     * 更新当前登陆的用户信息
     *
     * @param request
     * @param user
     * @return
     */
    public static boolean updateCurrentUser(HttpServletRequest request, User user) {
        return updateCurrentUserSession(request, user);
//        return updateCurrentUserRedis(request, user);
    }


    private static User getCurrentUserSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (User) session.getAttribute(Const.CURRENT_USER);
    }

    private static User getCurrentUserRedis(HttpServletRequest request) {
        String token = CookieUtil.readLoginToken(request);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        String userStr = RedisPoolUtil.get(token);
        return JsonUtil.string2Obj(userStr, User.class);
    }

    private static void saveCurrentUserSession(HttpSession session, User user) {
        session.setAttribute(Const.CURRENT_USER, user);
    }

    private static void saveCurrentUserRedis(HttpSession session, HttpServletResponse response, User user) {
        CookieUtil.writeLoginToken(response, session.getId());
        RedisPoolUtil.setEx(session.getId(), JsonUtil.obj2String(user), Const.RedisCacheExTime.REDIS_SESSION);
    }

    private static void deleteCurrentUserSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(Const.CURRENT_USER);
    }

    private static void deleteCurrentUserRedis(HttpServletRequest request, HttpServletResponse response) {
        String token = CookieUtil.readLoginToken(request);
        CookieUtil.delLoginToken(request, response);
        RedisPoolUtil.del(token);
    }

    private static boolean updateCurrentUserSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute(Const.CURRENT_USER, user);
        return true;
    }

    private static boolean updateCurrentUserRedis(HttpServletRequest request, User user) {
        String token = CookieUtil.readLoginToken(request);
        if (StringUtils.isBlank(token)) {
            return false;
        }
        RedisPoolUtil.setEx(token, JsonUtil.obj2String(user), Const.RedisCacheExTime.REDIS_SESSION);
        return true;
    }
}
