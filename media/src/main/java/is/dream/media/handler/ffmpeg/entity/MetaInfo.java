package is.dream.media.handler.ffmpeg.entity;

import lombok.Data;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/15 19:52
 */
@Data
public class MetaInfo {

    /**
     * 多媒体的大小，指的是存储体积，单位为B
     * （某些系统返回的大小可能为0）
     */
    private Long size;
    /**
     * 格式
     */
    private String format;
}
