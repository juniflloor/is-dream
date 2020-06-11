package is.dream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/8 10:23
 */
@EnableDiscoveryClient
@EnableFeignClients
@EnableZuulProxy
@SpringBootApplication
public class GateApplication {
      public static void main(String [] args) { SpringApplication.run(GateApplication.class,args);}
}
