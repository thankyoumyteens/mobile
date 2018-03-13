package iloveyesterday.mobile.controller.portal;

import com.google.common.collect.Maps;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseCode;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.IFileService;
import iloveyesterday.mobile.service.IUserService;
import iloveyesterday.mobile.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private IFileService fileService;

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
            session.setAttribute(Const.CURRENT_USER, responseData.getData());
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
        session.removeAttribute(Const.CURRENT_USER);
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
     * @param session
     * @return
     */
    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
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
     * @param session
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @RequestMapping(value = "reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData resetPassword(HttpSession session, String passwordOld, String passwordNew) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        return userService.resetPassword(user, passwordOld, passwordNew);
    }

    /**
     * 更新用户信息
     *
     * @param session
     * @param user
     * @return
     */
    @RequestMapping(value = "update_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<User> updateUserInfo(HttpSession session, User user) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        // 防止userId被修改
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        user.setCreateTime(currentUser.getCreateTime());
        // 权限不可修改
        user.setRole(currentUser.getRole());

        ResponseData<User> responseData = userService.updateUserInfo(user);
        if (responseData.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, responseData.getData());
        }
        return responseData;
    }

//    /**
//     * 获取用户信息
//     *
//     * @param session
//     * @return
//     */
//    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseData<User> getUserInfo(HttpSession session) {
//        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
//        if (currentUser == null) {
//            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
//        }
//        return userService.getUserInfo(currentUser.getId());
//    }

    /**
     * 上传头像
     * @param session
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("upload.do")
    @ResponseBody
    public ResponseData<Map> upload(HttpSession session, @RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        String path = request.getSession().getServletContext().getRealPath("upload");
        // 验证是否是图片
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!StringUtils.equals(fileExtensionName, "jpg")) {
            return ResponseData.error("请上传jpg格式图片");
        }
        // 上传
        String targetFileName = fileService.upload(file, path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

        Map fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFileName);
        fileMap.put("url", url);
        return ResponseData.success(fileMap);
    }

}
