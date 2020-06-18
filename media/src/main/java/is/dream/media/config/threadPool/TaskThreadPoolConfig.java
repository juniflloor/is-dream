package is.dream.media.config.threadPool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/18 13:01
 */
@Data
@ConfigurationProperties(prefix = "task.pool")
@Component
public class TaskThreadPoolConfig {

    private int corePoolSize;

    private int maxPoolSize;

    private int keepAliveSeconds;

    private int queueCapacity;
}
