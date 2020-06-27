package is.dream.dao.inter;

import is.dream.dao.entiry.VideoComment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/26 7:38
 */
@Mapper
public interface VideoCommentDao {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "videoId", column = "videoId"),
            @Result(property = "commentSessionId", column = "commentSessionId"),
            @Result(property = "parentId", column = "parentId"),
            @Result(property = "userId", column = "userId"),
            @Result(property = "content", column = "content"),
            @Result(property = "createTime", column = "createTime")
    })

    @Insert("INSERT into VideoComment values(#{id},#{videoId},#{commentSessionId},#{parentId},#{userId},#{content},#{createTime})")
    void save(VideoComment videoComment);

    @Select("SELECT * FROM VideoComment WHERE videoId=#{videoId} AND parentId='FIRST' ORDER BY createTime LIMIT #{startIndex},5")
    List<VideoComment> getByVideoId(String videoId,int startIndex);

    @Select("SELECT * FROM VideoComment WHERE id=#{id}")
    VideoComment getById(String id);

    @Select({
            "<script>",
            "SELECT * FROM VideoComment WHERE commentSessionId in",
            "<foreach collection='commentSessionIdList' item='item' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"
    })
    List<VideoComment> getByCommentSessionIdIn(@Param("commentSessionIdList") List<String> commentSessionIdList);

}
