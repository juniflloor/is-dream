package is.dream.dao.entiry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.sql.Timestamp;


/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/26 7:34
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoComment {

    private String id;

    private String videoId;

    private String commentSessionId;

    private String parentId;

    private String userId;

    private String content;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;
}
