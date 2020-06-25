package is.dream.dao.entiry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.sql.Date;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/25 18:47
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoOperation {

    private String id;

    private String associatedVideoId;

    private String associatedUserId;

    private int like;

    private Date createTime;

    private Date updateTime;
}
