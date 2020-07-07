package is.dream.dao.base.service;

import is.dream.dao.entiry.ImageUi;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/21 20:17
 */
public interface ImageUiService {

    void save(ImageUi imageUi);

    List<ImageUi> getImageUiByAssociatedImageUiSettingId(String associatedImageUiSettingId);

    void deleteByAssociatedImageUiSettingId(String associatedImageUiSettingId);

    List<ImageUi> getByAssociatedImageUiSettingIdList(List<String> associatedImageUiSettingIdList);
}
