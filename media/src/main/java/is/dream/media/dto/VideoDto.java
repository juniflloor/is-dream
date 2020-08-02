package is.dream.media.dto;

import is.dream.dao.entiry.Video;
import lombok.Data;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/24 2:47
 */
@Data
public class VideoDto extends Video {

    private String imageUrl;

    private int like;

    private String liveVideoUrl;

}
