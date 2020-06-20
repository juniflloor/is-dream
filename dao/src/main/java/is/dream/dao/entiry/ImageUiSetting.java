package is.dream.dao.entiry;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/21 4:09
 */
@Data
@AllArgsConstructor
public class ImageUiSetting {

    private String id;

    private String imageLocation;

    private String width;

    private String high;

    private String remark;

    private Date createTime;

    private Date updateTime;
}
