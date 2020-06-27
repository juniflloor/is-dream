package is.dream.dao.inter;

import is.dream.dao.entiry.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/9 18:57
 */
@Mapper
public interface UserDao {

    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "userName", column = "userName"),
        @Result(property = "password", column = "password"),
        @Result(property = "token", column = "token"),
        @Result(property = "userHeadImageUrl", column = "userHeadImageUrl")
    })

    @Select("SELECT * FROM user WHERE userName = #{userName} and password = #{password}")
    User getByUserNameAndPassword(String userName, String password);

    @Update("UPDATE user set token = #{token} WHERE id = #{id}")
    void updateUserToken(String id,String token);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User getById(String id);;

    @Select({
            "<script>",
            "SELECT * FROM user WHERE id in",
            "<foreach collection='idList' item='item' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"
    })
    List<User> getByIdIn(@Param("idList") List<String> idList);
}
