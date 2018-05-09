package iloveyesterday.mobile.controller.portal;

import com.google.common.collect.Maps;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.IFileService;
import iloveyesterday.mobile.service.IUserService;
import iloveyesterday.mobile.util.CookieUtil;
import iloveyesterday.mobile.util.JsonUtil;
import iloveyesterday.mobile.util.PropertiesUtil;
import iloveyesterday.mobile.util.RedisPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private IFileService fileService;

    // todo redis

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
            if (user.getRole() == Const.Role.USER) {
                CookieUtil.writeLoginToken(httpServletResponse, session.getId());
                RedisPoolUtil.setEx(session.getId(), JsonUtil.obj2String(user), Const.RedisCacheExTime.REDIS_SESSION);
            } else {
                return ResponseData.error("用户不存在");
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
     * 注册
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData register(User user) {
        return userService.register(user);
    }

    /**
     * 校验用户名或邮箱
     *
     * @param type
     * @param str
     * @return
     */
    @RequestMapping(value = "check_valid.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData checkValid(String type, String str) {
        return userService.checkValid(type, str);
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
        if (user.getRole() != Const.Role.USER) {
            return ResponseData.error("未登录");
        }
        return ResponseData.success(user);
    }

    /**
     * 获取找回密码问题
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "get_question.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<String> getQuestion(String username) {
        return userService.selectQuestion(username);
    }

    /**
     * 验证找回密码问题的答案
     *
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping(value = "check_answer.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<String> checkAnswer(String username, String question, String answer) {
        return userService.checkAnswer(username, question, answer);
    }

    /**
     * 修改密码
     *
     * @param username
     * @param password
     * @param token
     * @return
     */
    @RequestMapping(value = "question_reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData resetPasswordByQuestion(String username, String password, String token) {
        return userService.resetPasswordByQuestion(username, password, token);
    }

    /**
     * 登陆状态下修改密码
     *
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @RequestMapping(value = "reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData resetPassword(HttpServletRequest httpServletRequest, String passwordOld, String passwordNew) {
        ResponseData<User> data = getUserInfo(httpServletRequest);
        if (!data.isSuccess()) {
            return data;
        }
        User user = data.getData();
        return userService.resetPassword(user, passwordOld, passwordNew);
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "update_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<User> updateUserInfo(HttpServletRequest httpServletRequest, User user) {
        ResponseData<User> data = getUserInfo(httpServletRequest);
        if (!data.isSuccess()) {
            return data;
        }
        User currentUser = data.getData();
        // 防止userId被修改
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        user.setCreateTime(currentUser.getCreateTime());
        // 此处不修改头像
        user.setAvatar(currentUser.getAvatar());
        // 权限不可修改
        user.setRole(currentUser.getRole());

        ResponseData<User> responseData = userService.updateUserInfo(user);
        if (responseData.isSuccess()) {
            String token = CookieUtil.readLoginToken(httpServletRequest);
            if (StringUtils.isBlank(token)) {
                return ResponseData.error("请重新登陆");
            }
            RedisPoolUtil.setEx(token, JsonUtil.obj2String(responseData.getData()), Const.RedisCacheExTime.REDIS_SESSION);
        }
        return responseData;
    }

    /**
     * 更新用户头像
     *
     * @param avatar
     * @return
     */
    @RequestMapping(value = "update_avatar.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<User> updateUserAvatar(HttpServletRequest httpServletRequest, String avatar) {
        ResponseData<User> data = getUserInfo(httpServletRequest);
        if (!data.isSuccess()) {
            return data;
        }
        User currentUser = data.getData();

        ResponseData responseData = userService.updateUserAvatar(currentUser, avatar);
        if (responseData.isSuccess()) {
            currentUser.setAvatar(PropertiesUtil.getImageHost() + avatar);
            String token = CookieUtil.readLoginToken(httpServletRequest);
            if (StringUtils.isBlank(token)) {
                return ResponseData.error("请重新登陆");
            }
            RedisPoolUtil.setEx(token, JsonUtil.obj2String(currentUser), Const.RedisCacheExTime.REDIS_SESSION);
        }
        return responseData;
    }

    /**
     * 上传图片
     *
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("upload.do")
    @ResponseBody
    public ResponseData<Map> upload(HttpServletRequest httpServletRequest, @RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request) {
        ResponseData<User> data = getUserInfo(httpServletRequest);
        if (!data.isSuccess()) {
            return ResponseData.error("请登陆");
        }
        String path = request.getSession().getServletContext().getRealPath("upload");
        // 验证是否是图片
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!StringUtils.equals(fileExtensionName, "jpg") &&
                !StringUtils.equals(fileExtensionName, "png")) {
            return ResponseData.error("请上传jpg或png格式图片");
        }
        // 上传
        String targetFileName = fileService.upload(file, path);

        String url = PropertiesUtil.getImageHost() + targetFileName;

        Map fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFileName);
        fileMap.put("url", url);
        return ResponseData.success(fileMap);
    }

}
