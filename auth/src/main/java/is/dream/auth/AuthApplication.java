package is.dream.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/10 0:51
 */
@SpringBootApplication
@EnableEurekaClient
public class AuthApplication {
    public static void main(String [] args) { SpringApplication.run(AuthApplication.class,args);}
}
