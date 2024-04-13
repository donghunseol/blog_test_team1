package shop.mtcoding.blog.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJPARepository extends JpaRepository<User, Integer> {

    // 회원 가입 중복 체크
    Optional<User> findByUsername(@Param("username") String username);
    // 로그인을 위해 사용
    Optional<User> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
