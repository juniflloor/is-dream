package is.dream.dao.entiry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/7/11 11:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPlayVideoLog {

    private String id;

    private String associatedVideoId;

    private String associatedUserId;

    private Timestamp createTime;

    private Timestamp updateTime;
}
