package is.dream.dao.inter;

import is.dream.dao.entiry.ImageUiSetting;
import org.apache.ibatis.annotations.*;

import java.sql.Date;

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
            @Result(property = "remark", column = "remark"),
            @Result(property = "createTime", column = "createTime"),
            @Result(property = "updateTime", column = "updateTime")
    })

    @Insert("INSERT into ImageUiSetting values(#{id},#{imageLocation},#{width},#{high},#{remark},#{createTime},#{updateTime})")
    void save(ImageUiSetting imageUiSetting);

    @Update("UPDATE ImageUiSetting set width=#{width},high=#{high},remark=#{remark},updateTime=#{updateTime} WHERE imageLocation=#{imageLocation}")
    void updateByImageLocation(String imageLocation, int width, int high,String remark,Date updateTime);

    @Select("Select * from ImageUiSetting WHERE imageLocation=#{imageLocation}")
    ImageUiSetting getByImageLocation(String imageLocation);

}
