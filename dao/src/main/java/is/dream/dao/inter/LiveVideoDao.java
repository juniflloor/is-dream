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
            @Result(property = "startTime", column = "startTime"),
            @Result(property = "associatedVideoDuration", column = "associatedVideoDuration"),
            @Result(property = "isPlay", column = "isPlay"),
            @Result(property = "orderBy", column = "orderBy")
    })

    @Insert("INSERT into livevideo values(#{id},#{associatedVideoId},#{startTime},#{associatedVideoDuration},#{isPlay},#{orderBy})")
    void save(LiveVideo liveVideo);

    @Select("SELECT * FROM livevideo ORDEY BY orderBy")
    List<LiveVideo> getLiveVideo();

    @Select("SELECT * FROM livevideo where orderBy = 0")
    LiveVideo getStartLiveVideo();

    @Update("UPDATE livevideo set isPlay = 0")
    LiveVideo endVideo();

    @Update("UPDATE livevideo set isPlay = 1")
    LiveVideo resetIsPlay();
}
