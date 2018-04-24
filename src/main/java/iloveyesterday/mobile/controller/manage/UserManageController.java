package iloveyesterday.mobile.controller.manage;

import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseCode;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
    public ResponseData<User> login(String username, String password, HttpSession session) {
        ResponseData<User> responseData = userService.login(username, password);
        if (responseData.isSuccess()) {
            User user = responseData.getData();
            if (user.getRole() == Const.Role.SELLER) {
                session.setAttribute(Const.CURRENT_SELLER, responseData.getData());
            } else {
                return ResponseData.error("请商家登陆");
            }
        }
        return responseData;
    }

    /**
     * 登出
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_SELLER);
        return ResponseData.success();
    }

    /**
     * 获取用户信息
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_SELLER);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        return ResponseData.success(user);
    }

}
