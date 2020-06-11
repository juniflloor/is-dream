//package is.dream.gate.configure;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
///**
// * @author chendongzhao
// * @version 1.0
// * @date 2020/6/11 13:58
// */
//@Configuration
//public class SecurityPassAllConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        /* 配置允许任何类型请求的授权，并且关闭 csrf 保护 */
//        http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
//    }
//}
