package is.dream.dao.inter;

import is.dream.dao.entiry.ImageUi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

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

    @Select("SELECT * FROM ImageUi WHERE id = #{id}")
    ImageUi getByUserId(String userId);
}
