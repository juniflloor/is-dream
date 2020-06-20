package is.dream.dao.inter;

import is.dream.dao.entiry.Video;
import org.apache.ibatis.annotations.*;

import java.util.List;

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
            @Result(property = "sourceLocation", column = "sourceLocation"),
            @Result(property = "type", column = "type"),
            @Result(property = "tag", column = "tag"),
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

    @Insert("INSERT into Video values(#{id},#{name},#{sourceLocation},#{type},#{tag},#{title},#{year},#{coverImageUrl},#{duration},#{playUrl}," +
            "#{suffix},#{watchCount},#{commentCount},#{startNumber},#{likeCount},#{notLikeCount},#{introduction}," +
            "#{associatedCommentsId},#{createTime},#{updateTime})")
    void saveFull(Video video);

    @Select("Select * from video ORDER BY startNumber DESC limit 1")
    Video getHighestScore();

    @Select("Select * from video ORDER BY watchCount DESC limit 1")
    Video getHottest();

    @Select("Select * from video ORDER BY createTime DESC limit 4")
    List<Video> getNewest();

    @Select("SELECT * FROM video WHERE id = #{id}")
    Video getVideoById(String id);
}
