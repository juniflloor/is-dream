package is.dream.dao.entiry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/7/31 19:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LiveVideo {

    private String id;

    private String associatedVideoId;

    private long startTime;

    private int associatedVideoDuration;

    private int isPlay;

    private int orderBy;

}
