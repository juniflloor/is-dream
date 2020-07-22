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
            @Result(property = "fileName", column = "fileName"),
            @Result(property = "sourceLocation", column = "sourceLocation"),
            @Result(property = "type", column = "type"),
            @Result(property = "tag", column = "tag"),
            @Result(property = "title", column = "title"),
            @Result(property = "subtitle", column = "subtitle"),
            @Result(property = "year", column = "year"),
            @Result(property = "leadRole", column = "leadRole"),
            @Result(property = "defaultImageUrl", column = "defaultImageUrl"),
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

    @Insert("INSERT into Video values(#{id},#{name},#{fileName},#{sourceLocation},#{type},#{tag},#{title},#{subtitle},#{year},#{leadRole},#{defaultImageUrl}," +
            "#{duration},#{playUrl},#{suffix},#{watchCount},#{commentCount},#{startNumber},#{likeCount},#{notLikeCount},#{introduction}," +
            "#{associatedCommentsId},#{createTime},#{updateTime})")
    void saveFull(Video video);

    @Select("SELECT * FROM video WHERE id = #{id}")
    Video getVideoById(String id);


    @Select({
            "<script>",
            "SELECT * FROM video WHERE id in",
            "<foreach collection='idList' item='item' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"
    })
    List<Video> getByIdIn(@Param("idList") List<String> idList);

    @Update("UPDATE video set watchCount = watchCount + 1 WHERE id=#{id}")
    void addWatchCountById(String id);

    @Update("UPDATE video set likeCount = likeCount + 1 WHERE id=#{id}")
    void addLikeCountById(String id);

    @Select("SELECT * from video where name = #{name}")
    List<Video> searchByName(String name);

    @Select("SELECT * from video where type = #{type}")
    List<Video> searchByType(String type);

    @Select("SELECT * from video where tag = #{tag}")
    List<Video> searchByTag(String tag);

    @Select("SELECT * from video where leadRole = #{leadRole}")
    List<Video> searchByLeadRole(String leadRole);

    @Select("SELECT * from video where name like #{name} or type like #{type} or tag like #{tag} or leadRole like #{leadRole} limit 0,12")
    List<Video> searchVideo(String name,String type,String tag,String leadRole);

    @Select("SELECT * FROM video  ORDER BY watchCount DESC LIMIT 0,5")
    List<Video> getMostViewVideo();

}
