package is.dream;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/16 20:51
 */
@EnableAsync
@SpringBootApplication
@EnableEurekaClient
@MapperScan("is.dream.dao.inter")
@EnableSwagger2
public class MediaApplication {
    public static void main(String [] args) { SpringApplication.run(MediaApplication.class,args);}
}
