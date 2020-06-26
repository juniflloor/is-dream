package is.dream.dao.inter;

import is.dream.dao.entiry.Video;
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
            @Result(property = "commentId", column = "commentId"),
            @Result(property = "parentId", column = "parentId"),
            @Result(property = "userId", column = "userId"),
            @Result(property = "userName", column = "userName"),
            @Result(property = "headImageUrl", column = "headImageUrl"),
            @Result(property = "content", column = "content"),
            @Result(property = "createTime", column = "createTime")
    })

    @Insert("INSERT into VideoComment values(#{id},#{commentId},#{parentId},#{userId},#{userName},#{headImageUrl},#{content},#{createTime})")
    void save(VideoComment videoComment);

    @Select("SELECT * FROM VideoComment WHERE id=#{id}")
    List<VideoComment> getById(String id);

    @Select("SELECT * FROM VideoComment WHERE parentId=#{parentId}")
    List<VideoComment> getByParentId(String parentId);
}
