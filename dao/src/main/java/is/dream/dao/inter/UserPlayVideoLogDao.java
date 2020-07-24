package is.dream.dao.inter;

import is.dream.dao.entiry.UserPlayVideoLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/7/11 11:46
 */
@Mapper
public interface UserPlayVideoLogDao {


    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "associatedVideoId", column = "associatedVideoId"),
            @Result(property = "associatedUserId", column = "associatedUserId"),
            @Result(property = "createTime", column = "createTime"),
            @Result(property = "updateTime", column = "updateTime"),
    })

    @Insert("INSERT INTO userplayvideolog values(#{id},#{associatedVideoId},#{associatedUserId},#{createTime},#{updateTime})")
    void save(UserPlayVideoLog userPlayVideoLog);

    @Select("SELECT * FROM userplayvideolog WHERE associatedUserId = #{associatedUserId} limit 0, 10")
    List<UserPlayVideoLog> getByAssociatedUserId(String associatedUserId);

}
