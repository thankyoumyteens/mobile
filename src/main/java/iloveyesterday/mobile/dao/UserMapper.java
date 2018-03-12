package iloveyesterday.mobile.dao;

import iloveyesterday.mobile.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);


    /**
     * 检查用户名是否存在
     * @param username
     * @return 0 -> 不存在
     */
    int checkUsername(String username);

    /**
     * 检查邮箱是否存在
     * @param email
     * @return
     */
    int checkEmail(String email);

    /**
     * 检查邮箱是否存在
     * @param userId 除了这个用户之外
     * @param email
     * @return
     */
    int checkEmailByUserId(
            @Param("userId") Long userId,
            @Param("email") String email
    );

    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    User selectByUsernameAndPassword(
            @Param("username") String username,
            @Param("password") String password
    );

    /**
     * 根据用户名查询找回密码问题
     * @param username
     * @return
     */
    String selectQuestionByUsername(String username);

    int checkAnswer(
            @Param("username") String username,
            @Param("question") String question,
            @Param("answer") String answer
    );

    int updatePasswordByUsername(
            @Param("username") String username,
            @Param("password") String password
    );

    /**
     * 检查原始密码是否正确
     * @param userId 可能存在不同用户相同的密码, 防止横向越权
     * @param password
     * @return
     */
    int checkPassword(
            @Param("userId") Long userId,
            @Param("password") String password
    );
}