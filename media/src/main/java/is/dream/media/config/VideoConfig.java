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
     * 视频解截取封面图片 (正常的应该自己制作然后上传)
     */
    private String coverImagePath;

    /**
     * 封面图片url
     */
    private String imageUrl;

    /**
     * 视频访问地址
     */
    private String accessUrl;
}
