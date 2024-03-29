package is.dream.dao.inter;

import is.dream.dao.entiry.ImageUiSetting;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/21 4:14
 */
@Mapper
public interface ImageUiSettingDao {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "imageLocation", column = "imageLocation"),
            @Result(property = "width", column = "width"),
            @Result(property = "high", column = "high"),
            @Result(property = "weight", column = "weight"),
            @Result(property = "listLocation", column = "listLocation"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "createTime", column = "createTime"),
            @Result(property = "updateTime", column = "updateTime")
    })

    @Insert("INSERT into imageuisetting values(#{id},#{imageLocation},#{width},#{high},#{weight},#{listLocation},#{remark},#{createTime},#{updateTime})")
    void save(ImageUiSetting imageUiSetting);

    @Update("UPDATE imageuisetting set width=#{width},high=#{high},remark=#{remark},updateTime=#{updateTime} WHERE imageLocation=#{imageLocation}")
    void updateByImageLocation(String imageLocation, int width, int high,String remark,Date updateTime);

    @Select("Select * from imageuisetting WHERE imageLocation=#{imageLocation}")
    ImageUiSetting getByImageLocation(String imageLocation);

    @Select("Select * from imageuisetting WHERE imageLocation like #{imageLocation} ORDER BY listLocation")
    List<ImageUiSetting> getByImageLocationLike(String imageLocation);

}
