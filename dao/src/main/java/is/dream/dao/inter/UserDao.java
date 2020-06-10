package is.dream.dao.inter;

import is.dream.dao.entiry.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/9 18:57
 */
@Mapper
public interface UserDao {

    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "userId", column = "userId"),
        @Result(property = "userName", column = "userName"),
        @Result(property = "password", column = "password"),
        @Result(property = "token", column = "token"),
        @Result(property = "userHeadImageUrl", column = "userHeadImageUrl")
    })

    @Select("SELECT * FROM user WHERE userName = #{userName} and password = #{password}")
    User getByUserNameAndPassword(String userName, String password);

    @Update("UPDATE user set token = #{token} WHERE userId = #{userId}")
    void updateUserToken(String userId,String token);

    @Update("SELECT * FROM user WHERE userId = #{userId}")
    User getByUserId(String userId);;
}
