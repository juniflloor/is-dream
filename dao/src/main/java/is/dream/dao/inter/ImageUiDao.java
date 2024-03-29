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

    @Insert("INSERT into imageui values(#{id},#{imageUrl},#{associatedImageUiSettingId},#{associatedVideoId},#{createTime},#{updateTime})")
    void save(ImageUi imageUi);

    @Select("SELECT * FROM imageui WHERE associatedImageUiSettingId=#{associatedImageUiSettingId}")
    List<ImageUi> getImageUiByAssociatedImageUiSettingId(String associatedImageUiSettingId);

    @Delete("DELETE FROM imageui WHERE associatedImageUiSettingId = #{associatedImageUiSettingId}")
    void deleteByAssociatedImageUiSettingId(String associatedImageUiSettingId);

    @Select({
            "<script>",
            "SELECT * FROM imageui WHERE associatedImageUiSettingId in",
            "<foreach collection='associatedImageUiSettingIdList' item='item' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"
    })
    List<ImageUi> getByAssociatedImageUiSettingIdList(@Param("associatedImageUiSettingIdList") List<String> associatedImageUiSettingIdList);
}
