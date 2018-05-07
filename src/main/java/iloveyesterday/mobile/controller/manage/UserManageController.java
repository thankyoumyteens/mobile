package iloveyesterday.mobile.controller.manage;

import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.IUserService;
import iloveyesterday.mobile.util.CookieUtil;
import iloveyesterday.mobile.util.JsonUtil;
import iloveyesterday.mobile.util.RedisPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/user/")
public class UserManageController {

    @Resource
    private IUserService userService;

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData register(User user) {
        user.setRole(Const.Role.SELLER);
        return userService.register(user);
    }


    /**
     * 登陆
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<User> login(String username, String password, HttpSession session, HttpServletResponse httpServletResponse) {
        ResponseData<User> responseData = userService.login(username, password);
        if (responseData.isSuccess()) {
            User user = responseData.getData();
            if (user.getRole() == Const.Role.SELLER) {
                CookieUtil.writeLoginToken(httpServletResponse, session.getId());
                RedisPoolUtil.setEx(session.getId(), JsonUtil.obj2String(user), Const.RedisCacheExTime.REDIS_SESSION);
            } else {
                return ResponseData.error("请商家登陆");
            }
        }
        return responseData;
    }

    /**
     * 登出
     *
     * @return
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String token = CookieUtil.readLoginToken(httpServletRequest);
        CookieUtil.delLoginToken(httpServletRequest, httpServletResponse);
        RedisPoolUtil.del(token);
        return ResponseData.success();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<User> getUserInfo(HttpServletRequest httpServletRequest) {
        String token = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isBlank(token)) {
            return ResponseData.error("未登录");
        }
        String userStr = RedisPoolUtil.get(token);
        User user = JsonUtil.string2Obj(userStr, User.class);
        if (user == null) {
            return ResponseData.error("未登录");
        }
        return ResponseData.success(user);
    }

}
