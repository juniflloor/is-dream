package is.dream.media.dto;

import is.dream.dao.entiry.VideoComment;
import lombok.Data;

import java.util.List;


/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/27 11:30
 */

@Data
public class VideoCommentDto extends VideoComment {

    private String userName;

    private String headImageUrl;

    private List<VideoCommentDto> commentDtoList;
}
