package is.dream.gate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/9 16:36
 * A uniform entity format for the request response
 */
@Data
@AllArgsConstructor
public class Result<T>{

    private String code;

    private String message;

    private T data;
}
