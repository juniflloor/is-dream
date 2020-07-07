package is.dream.dao.entiry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageUiSetting {

    private String id;

    private String imageLocation;

    private int width;

    private int high;

    private int weight;

    private int listLocation;

    private String remark;

    private Date createTime;

    private Date updateTime;
}
