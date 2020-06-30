package is.dream.auth.dto;

import is.dream.dao.entiry.User;
import lombok.Data;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/30 12:28
 */
@Data
public class UserDto extends User {

    public UserDto(String id, String userName, String password, String email, String userHeadImageUrl, String token) {
        super(id, userName, password, email, userHeadImageUrl, token);
    }

    private String code;
}
