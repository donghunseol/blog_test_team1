package shop.mtcoding.blog._core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import shop.mtcoding.blog.user.SessionUser;
import shop.mtcoding.blog.user.User;

import java.util.Date;

public class JwtUtil {
    public static String create(User user){
        String jwt = JWT.create()
                .withSubject("blog")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24))
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .sign(Algorithm.HMAC256("sdhcoding"));
        return jwt;
    }

    public static SessionUser verify(String jwt){
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("sdhcoding")).build().verify(jwt);
        Integer id = decodedJWT.getClaim("id").asInt();
        String username = decodedJWT.getClaim("username").asString();

        return SessionUser.builder()
                .id(id)
                .username(username)
                .build();
    }
}
