package is.dream.dao.entiry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/9 18:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String id;

    private String userName;

    private String password;

    private String email;

    private String userHeadImageUrl;

    private String token;
}
