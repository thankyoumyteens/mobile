package iloveyesterday.mobile.service.impl;

import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.common.TokenCache;
import iloveyesterday.mobile.dao.UserMapper;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.IUserService;
import iloveyesterday.mobile.util.MD5Util;
import iloveyesterday.mobile.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public ResponseData<User> login(String username, String password) {
        ResponseData responseData = this.checkValid(Const.USERNAME, username);
        if (responseData.isSuccess()) {
            return ResponseData.error("用户不存在");
        }
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectByUsernameAndPassword(username, md5Password);
        if (user == null) {
            return ResponseData.error("密码错误");
        }
        // 密码置空
        user.setPassword(StringUtils.EMPTY);
        // 设置图片路径
        user.setAvatar(PropertiesUtil.getProperty("ftp.server.http.prefix") + user.getAvatar());
        return ResponseData.success("登陆成功", user);
    }

    public ResponseData register(User user) {
        ResponseData responseData = this.checkValid(Const.USERNAME, user.getUsername());
        if (!responseData.isSuccess()) {
            return responseData;
        }
        responseData = this.checkValid(Const.EMAIL, user.getEmail());
        if (!responseData.isSuccess()) {
            return responseData;
        }
        // 默认普通用户
        user.setRole(Const.Role.USER);
        String md5Password = MD5Util.MD5EncodeUtf8(user.getPassword());
        user.setPassword(md5Password);

        user.setAvatar("1.jpg");

        int resultCount = userMapper.insert(user);
        if (resultCount == 0) {
            return ResponseData.error("注册失败");
        }
        return ResponseData.successMessage("注册成功");
    }

    public ResponseData checkValid(String type, String str) {
        if (StringUtils.isBlank(type)) {
            return ResponseData.error("参数错误");
        }
        int resultCount = -1;
        switch (type) {
            case Const.USERNAME:
                resultCount = userMapper.checkUsername(str);
                if (resultCount > 0) {
                    return ResponseData.error("用户已存在");
                }
                break;
            case Const.EMAIL:
                resultCount = userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return ResponseData.error("邮箱已被使用");
                }
                break;
        }
        return ResponseData.successMessage("校验成功");
    }

    public ResponseData<String> selectQuestion(String username) {
        ResponseData responseData = this.checkValid(Const.USERNAME, username);
        if (responseData.isSuccess()) {
            return ResponseData.error("用户不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if (StringUtils.isBlank(question)) {
            return ResponseData.error("问题为空");
        }
        return ResponseData.success(question);
    }

    public ResponseData<String> checkAnswer(String username, String question, String answer) {
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if (resultCount == 0) {
            return ResponseData.error("答案错误");
        }
        String token = UUID.randomUUID().toString();
        // 将token放入本地缓存
        TokenCache.setKey(username, token);
        return ResponseData.success(token);
    }

    public ResponseData resetPasswordByQuestion(String username, String password, String token) {
        if (StringUtils.isBlank(token)) {
            return ResponseData.error("参数错误");
        }
        ResponseData responseData = this.checkValid(Const.USERNAME, username);
        if (responseData.isSuccess()) {
            return ResponseData.error("用户不存在");
        }
        String tokenLocal = TokenCache.getKey(username);
        if (StringUtils.isBlank(tokenLocal)) {
            return ResponseData.error("token失效");
        }
        if (!StringUtils.equals(token, tokenLocal)) {
            return ResponseData.error("token错误");
        }
        // 修改密码
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        int resultCount = userMapper.updatePasswordByUsername(username, md5Password);
        if (resultCount == 0) {
            return ResponseData.error("修改密码失败");
        }
        return ResponseData.successMessage("修改密码成功");
    }

    public ResponseData resetPassword(User user, String passwordOld, String passwordNew) {
        int resultCount = userMapper.checkPassword(user.getId(), MD5Util.MD5EncodeUtf8(passwordOld));
        if (resultCount == 0) {
            return ResponseData.error("原始密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        resultCount = userMapper.updateByPrimaryKeySelective(user);
        if (resultCount == 0) {
            return ResponseData.error("修改失败");
        }
        return ResponseData.successMessage("修改成功");
    }

    public ResponseData<User> updateUserInfo(User user) {
        int resultCount = userMapper.checkEmailByUserId(user.getId(), user.getEmail());
        if (resultCount > 0) {
            return ResponseData.error("邮箱已被使用");
        }
        User userForUpdate = new User();
        // username不能修改
        userForUpdate.setId(user.getId());
        userForUpdate.setEmail(user.getEmail());
        userForUpdate.setPhone(user.getPhone());
        userForUpdate.setNickname(user.getNickname());
        userForUpdate.setAvatar(user.getAvatar());
        userForUpdate.setQuestion(user.getQuestion());
        userForUpdate.setAnswer(user.getAnswer());
        // 赋值updateTime, 记录更新时间
        userForUpdate.setUpdateTime(new Date());

        resultCount = userMapper.updateByPrimaryKeySelective(userForUpdate);
        if (resultCount == 0) {
            return ResponseData.error("修改失败");
        }
        userForUpdate.setUsername(user.getUsername());
        userForUpdate.setRole(user.getRole());
        userForUpdate.setCreateTime(user.getCreateTime());
        // 设置图片路径
        userForUpdate.setAvatar(PropertiesUtil.getProperty("ftp.server.http.prefix")
                + userForUpdate.getAvatar());
        return ResponseData.success("修改成功", userForUpdate);
    }

    @Override
    public ResponseData<User> getUserInfo(Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null){
            return ResponseData.error("用户不存在");
        }
        user.setPassword(StringUtils.EMPTY);
        // 设置图片路径
        user.setAvatar(PropertiesUtil.getProperty("ftp.server.http.prefix") + user.getAvatar());
        return ResponseData.success(user);
    }

}
