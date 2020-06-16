package is.dream.dao.entiry;

import lombok.Data;

import java.sql.Timestamp;

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

    private String year;

    private String coverImageUrl;

    private Long duration;;

    private String playUrl;

    private String suffix;

    private long watchCount;

    private long commentCount;

    private int startNumber;

    private int likeCount;

    private int notLikeCount;

    private String introduction;

    private String associatedCommentsId;

    private Timestamp createTime;

    private Timestamp updateTime;

    public void setDefault(){
        this.setLikeCount(0);
        this.setCommentCount(0);
        this.setNotLikeCount(0);
        this.setStartNumber(0);
        this.setWatchCount(0);
    }
}
