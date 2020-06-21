package is.dream.dao.inter;

import is.dream.dao.entiry.ImageUi;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/21 4:02
 */
@Mapper
public interface ImageUiDao {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "imageUrl", column = "imageUrl"),
            @Result(property = "associatedImageUiSettingId", column = "associatedImageUiSettingId"),
            @Result(property = "associatedVideoId", column = "associatedVideoId"),
            @Result(property = "createTime", column = "createTime"),
            @Result(property = "updateTime", column = "updateTime")
    })

    @Insert("INSERT into ImageUi values(#{id},#{imageUrl},#{associatedImageUiSettingId},#{associatedVideoId},#{createTime},#{updateTime})")
    void save(ImageUi imageUi);

    @Select("SELECT * FROM ImageUi WHERE associatedImageUiSettingId=#{associatedImageUiSettingId}")
    List<ImageUi> getImageUiByAssociatedImageUiSettingId(String associatedImageUiSettingId);
}
