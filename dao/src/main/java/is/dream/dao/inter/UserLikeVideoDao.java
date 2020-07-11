package is.dream.dao.inter;

import is.dream.dao.entiry.UserLikeVideo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/7/11 15:17
 */
@Mapper
public interface UserLikeVideoDao {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "associatedVideoId", column = "associatedVideoId"),
            @Result(property = "associatedUserId", column = "associatedUserId"),
            @Result(property = "createTime", column = "createTime"),
            @Result(property = "updateTime", column = "updateTime"),
    })

    @Insert("INSERT INTO userLikeVideo values(#{id},#{associatedVideoId},#{associatedUserId},#{createTime},#{updateTime})")
    void save(UserLikeVideo userLikeVideo);

    @Select("SELECT * FROM userLikeVideo WHERE associatedUserId = #{associatedUserId} limit 0, 10")
    List<UserLikeVideo> getByAssociatedUserId(String associatedUserId);

}
