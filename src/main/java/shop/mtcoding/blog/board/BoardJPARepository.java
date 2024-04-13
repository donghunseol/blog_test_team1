package shop.mtcoding.blog.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardJPARepository extends JpaRepository<Board, Integer> {

    // 글 상세 보기에 사용
    @Query("SELECT b FROM Board b JOIN FETCH b.user u WHERE b.id = :id")
    Optional<Board> findByJoinUser(@Param("id") Integer id);
}
