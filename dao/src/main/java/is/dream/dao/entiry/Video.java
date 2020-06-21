package is.dream.dao.entiry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.sql.Date;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/16 3:25
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Video {

    private String id;

    private String name;

    private String sourceLocation;

    private String type;

    private String tag;

    private String title;

    private String year;

    private String defaultImageUrl;

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

    private Date createTime;

    private Date updateTime;

    public void setDefault(){
        this.setLikeCount(0);
        this.setCommentCount(0l);
        this.setNotLikeCount(0);
        this.setStartNumber(0);
        this.setWatchCount(0l);
    }
}
