package shop.mtcoding.blog.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class BoardJPARepositoryTest {

    @Autowired
    private BoardJPARepository boardJPARepository;

    @Test
    public void findByJoinUser_test(){
        // given
        Integer id = 1;

        // when
        Optional<Board> board = boardJPARepository.findByJoinUser(id);

        // eye
        System.out.println(board.get());

        // then
        Assertions.assertThat(board.get().getUser().getUsername()).isEqualTo("ssar");
    }
}
