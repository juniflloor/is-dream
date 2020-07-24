package is.dream.dao.inter;

import is.dream.dao.entiry.VideoOperation;
import org.apache.ibatis.annotations.*;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/25 18:49
 */
@Mapper
public interface VideoOperationDao {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "associatedVideoId", column = "associatedVideoId"),
            @Result(property = "associatedUserId", column = "associatedUserId"),
            @Result(property = "like", column = "like"),
            @Result(property = "createTime", column = "createTime"),
            @Result(property = "updateTime", column = "updateTime")
    })

    @Insert("INSERT into videooperation values(#{id},#{associatedVideoId},#{associatedUserId},#{like},#{createTime},#{updateTime})")
    void save(VideoOperation videoOperation);

    @Select("SELECT * FROM videooperation WHERE associatedVideoId=#{associatedVideoId} AND associatedUserId=#{associatedUserId}")
    VideoOperation getByAssociatedId(String associatedVideoId,String associatedUserId);
}
