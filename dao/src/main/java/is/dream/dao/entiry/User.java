package is.dream.dao.entiry;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/9 18:52
 */
@Data
@AllArgsConstructor
public class User {

    private long id;

    private String userId;

    private String userName;

    private String password;

    private String userHeadImageUrl;

    private String token;
}
