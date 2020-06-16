package is.dream.dao.inter;

import is.dream.dao.entiry.Video;
import org.apache.ibatis.annotations.*;

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
            @Result(property = "year", column = "year"),
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
    Video getByUserId(String id);

    @Insert("INSERT into Video values(#{id}, #{name},#{title},#{year},#{coverImageUrl},#{duration},#{playUrl}," +
            "#{suffix},#{watchCount},#{commentCount},#{startNumber},#{likeCount},#{notLikeCount},#{introduction}," +
            "#{associatedCommentsId},#{createTime},#{updateTime},)")
    void saveFull(Video video);
    
}
