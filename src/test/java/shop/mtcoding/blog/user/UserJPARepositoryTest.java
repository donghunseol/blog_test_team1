package shop.mtcoding.blog.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class UserJPARepositoryTest {

    @Autowired
    private UserJPARepository userJPARepository;

    @Test
    public void findByUsernameAndPassword_test(){
        // given
        String username = "ssar";
        String password = "1234";

        // when
        Optional<User> user = userJPARepository.findByUsernameAndPassword(username, password);

        // eye
        System.out.println(user);

        // then
        Assertions.assertThat(user.get().getUsername()).isEqualTo("ssar");
    }

    @Test
    public void findByUsername_test(){
        // given
        String username = "ssar";

        // when
        Optional<User> user = userJPARepository.findByUsername(username);

        // eye
        System.out.println(user);

        // then
        Assertions.assertThat(user.get().getUsername()).isEqualTo("ssar");
    }
}
