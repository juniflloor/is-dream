package is.dream.dao.entiry;

import lombok.Data;

import java.sql.Date;

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

    private Date createTime;

    private Date updateTime;
}
