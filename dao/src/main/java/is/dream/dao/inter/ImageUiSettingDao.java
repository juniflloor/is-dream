package is.dream.dao.inter;

import is.dream.dao.entiry.ImageUiSetting;
import org.apache.ibatis.annotations.*;

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

    @Insert("INSERT into ImageUiSetting values(#{id},#{imageLocation},#{width},#{high},#{createTime},#{updateTime}")
    void save(ImageUiSetting imageUiSetting);

    @Update("UPDATE ImageUiSetting set width=#{width},high=#{high} WHERE imageLocation=#{imageLocation}")
    void updateByImageLocation(String imageLocation,String width, String high);

}
