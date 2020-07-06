package is.dream.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import is.dream.dao.entiry.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/30 12:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto extends User {

    public UserDto(String id, String userName, String password, String email, String userHeadImageUrl, String token) {
        super(id, userName, password, email, userHeadImageUrl, token);
    }

    private String code;
}
