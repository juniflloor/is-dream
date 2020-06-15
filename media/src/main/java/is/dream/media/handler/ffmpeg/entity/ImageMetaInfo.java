package is.dream.media.handler.ffmpeg.entity;

import lombok.Data;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/15 19:53
 */
@Data
public class ImageMetaInfo extends MetaInfo{

     /** 图片宽度，单位为px
     */
    private Integer width;
    /**
     * 图片高度，单位为px
     */
    private Integer height;
}
