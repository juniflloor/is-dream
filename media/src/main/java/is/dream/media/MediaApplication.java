package is.dream.media;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/16 20:51
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("is.dream.dao.inter")
public class MediaApplication {
}
