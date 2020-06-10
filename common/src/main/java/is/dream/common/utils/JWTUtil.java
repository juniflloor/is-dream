package is.dream.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/10 14:06
 */
public class JWTUtil {

    private static final String JWT_SECRET_KEY = "#isdreamgo$";
    private static final String JWT_EXPIRE = "expire";
    private static final String JWT_PAYLOAD = "payload";
    public static final String TOKEN = "token";

    public static <T> String createToken(T object, long time) {
        try {
            final Map<String, Object> claims = new HashMap<>();
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(object);
            claims.put(JWT_PAYLOAD, jsonString);
            claims.put(JWT_EXPIRE, System.currentTimeMillis() + time);
            String token = Jwts.builder()
                    .setSubject(TOKEN)
                    .setClaims(claims)
                    .setIssuedAt(new Date())
                    .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY).compact();
            return token;
        } catch (Exception e) {

            return null;
        }
    }

    public static <T> T deciphering(String token, Class<T> clazz) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token).getBody();
            Long expire =  (long) claims.get(JWT_EXPIRE);
            Long currentTimeMillis = System.currentTimeMillis();
            if (expire.compareTo(currentTimeMillis) < 0 ) {
                return null;
            }
            String payload =  (String) claims.get(JWT_PAYLOAD);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(payload, clazz);
        } catch (Exception e) {

            return null;
        }
    }

}
