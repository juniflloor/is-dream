package is.dream.dao.inter;

import is.dream.dao.entiry.User;
import is.dream.dao.entiry.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Update;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/16 3:54
 */
@Mapper
public interface VideoDao {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "title", column = "title"),
            @Result(property = "uploadTime", column = "uploadTime"),
            @Result(property = "coverImageUrl", column = "coverImageUrl"),
            @Result(property = "duration", column = "duration"),
            @Result(property = "playUrl", column = "playUrl"),
            @Result(property = "suffix", column = "suffix"),
            @Result(property = "watchCount", column = "watchCount"),
            @Result(property = "commentCount", column = "commentCount"),
            @Result(property = "startNumber", column = "startNumber"),
            @Result(property = "likeCount", column = "likeCount"),
            @Result(property = "notLikeCount", column = "notLikeCount"),
            @Result(property = "introduction", column = "introduction"),
            @Result(property = "associatedCommentsId", column = "associatedCommentsId"),
            @Result(property = "createTime", column = "createTime"),
            @Result(property = "updateTime", column = "updateTime")
    })

    @Update("SELECT * FROM video WHERE id = #{id}")
    Video getByUserId(String id);;
}
