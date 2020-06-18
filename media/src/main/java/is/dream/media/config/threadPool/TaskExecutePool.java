package is.dream.media.config.threadPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/18 13:03
 */
@Configuration
public class TaskExecutePool {

    @Autowired
    private TaskThreadPoolConfig config;

    @Bean
    public Executor myTaskAsyncPool() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(config.getCorePoolSize());

        executor.setMaxPoolSize(config.getMaxPoolSize());

        executor.setQueueCapacity(config.getQueueCapacity());

        executor.setKeepAliveSeconds(config.getKeepAliveSeconds());

        executor.setThreadNamePrefix("MyExecutor-");

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.initialize();

        return executor;
    }
}
