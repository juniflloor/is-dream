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

    private String localPath;

    private String accessUrl;
}
