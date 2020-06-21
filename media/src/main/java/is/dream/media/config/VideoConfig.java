package is.dream.media.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/16 5:17
 */
@Data
@ConfigurationProperties(prefix = "video")
@Component
public class VideoConfig {

    /**
     * 上传视频存放路径
     */
    private String sourcePath;

    /**
     * 视频解析后存放路径
     */
    private String targetPath;

    /**
     * 视频解截取默认图片 (正常的应该自己制作然后上传)
     */
    private String imageDefaultPath;

    /**
     * 默认图片图片URL
     */
    private String imageDefaultUrl;

    /**
     * 视频前端展示地址
     */
    private String imageUIPath;
    /**
     * 视频前端展示URL
     */
    private String imageUIUrl;

    /**
     * 视频访问URL
     */
    private String accessUrl;
}
