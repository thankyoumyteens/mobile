package iloveyesterday.mobile.service;

import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.User;

public interface IUserService {
    ResponseData<User> login(String username, String password);

    ResponseData register(User user);

    /**
     * 检验用户名或邮箱是否存在
     * @param type
     * @param str
     * @return isSuccess() -> 不存在
     */
    ResponseData checkValid(String type, String str);

    ResponseData<String> selectQuestion(String username);

    ResponseData<String> checkAnswer(String username, String question, String answer);

    ResponseData resetPasswordByQuestion(String username, String password, String token);

    ResponseData resetPassword(User user, String passwordOld, String passwordNew);

    ResponseData<User> updateUserInfo(User user);

    ResponseData<User> getUserInfo(Long userId);

    ResponseData updateUserAvatar(User user, String avatar);
}
