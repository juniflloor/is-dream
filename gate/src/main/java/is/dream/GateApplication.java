package is.dream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/8 10:23
 */
@EnableEurekaClient
@SpringBootApplication
public class GateApplication {
      public static void main(String [] args) { SpringApplication.run(GateApplication.class,args);}
}
