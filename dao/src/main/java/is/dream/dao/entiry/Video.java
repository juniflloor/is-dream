package is.dream.dao.entiry;

import lombok.Data;

import java.util.Date;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/16 3:25
 */
@Data
public class Video {

    private String id;

    private String name;

    private String title;

    private Date uploadTime;

    private String coverImageUrl;

    private String duration;;

    private String playUrl;

    private String suffix;

    private long watchCount;

    private long commentCount;

    private int startNumber;

    private int likeCount;

    private int notLikeCount;

    private String introduction;

    private String associatedCommentsId;

    private Date createTime;

    private Date updateTime;
}
