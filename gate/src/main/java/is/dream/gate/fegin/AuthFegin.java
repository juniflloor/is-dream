package is.dream.gate.fegin;

import is.dream.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/11 2:32
 */
@FeignClient( value = "dream-auth" )
public interface AuthFegin {

    @PostMapping( value = "" )
    Result<Object> checkTokenIsLawful(@RequestParam("token") String token);
}
