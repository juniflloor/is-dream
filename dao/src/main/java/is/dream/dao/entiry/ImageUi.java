package is.dream.dao.entiry;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/21 3:15
 */
@Data
public class ImageUi {

    private String id;

    private String imageUrl;

    private String associatedImageUiSettingId;

    private String associatedVideoId;

    private Timestamp createTime;

    private Timestamp updateTime;
}
