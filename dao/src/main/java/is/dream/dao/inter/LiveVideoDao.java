package is.dream.dao.inter;

import is.dream.dao.entiry.LiveVideo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/7/31 23:58
 */
@Mapper
public interface LiveVideoDao {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "associatedVideoId", column = "associatedVideoId"),
            @Result(property = "isPlay", column = "isPlay"),
            @Result(property = "orderBy", column = "orderBy")
    })

    @Insert("INSERT into livevideo values(#{id},#{associatedVideoId},#{isPlay},#{orderBy})")
    void save(LiveVideo liveVideo);

    @Select("SELECT * FROM livevideo ORDEY BY orderBy")
    List<LiveVideo> getLiveVideo();

    @Select("SELECT * FROM livevideo where orderBy = #{orderBy}")
    LiveVideo getLiveVideoByOrderBy(int orderBy);

    @Select("SELECT * FROM livevideo")
    List<LiveVideo> getLiveVideoList();

    @Select("SELECT max(orderBy) FROM livevideo")
    Integer getMaxOrderBy();

    @Update("UPDATE livevideo set isPlay = 0")
    void endVideo();

    @Update("UPDATE livevideo set isPlay = 1")
    void resetIsPlay();

}
