package is.dream.dao.entiry;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/21 4:09
 */
@Data
public class ImageUiSetting {

    private String id;

    private String imageLocation;

    private String width;

    private String high;

    private String remark;

    private Timestamp createTime;

    private Timestamp updateTime;
}
