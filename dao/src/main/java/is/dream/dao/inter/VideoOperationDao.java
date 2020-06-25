package is.dream.dao.inter;

import is.dream.dao.entiry.VideoOperation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

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

    @Insert("INSERT into VideoOperation values({id},#{associatedVideoId},#{associatedUserId},#{like},#{createTime},#{updateTime}")
    void save(VideoOperation videoOperation);

    @Insert("SELECT * FROM VideoOperation WHERE associatedVideoId=#{associatedVideoId} AND associatedUserId=#{associatedUserId}")
    VideoOperation getByAssociatedId(String associatedVideoId,String associatedUserId);
}
