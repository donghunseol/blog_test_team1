package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor // final이 붙은 친구들의 생성자를 만들어줘
@Controller // new BoardController(IoC에서 BoardRepository를 찾아서 주입) -> IoC 컨테이너 등록
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final HttpSession session;

    // 게시글 작성
    @PostMapping("/api/boards")
    public String save(BoardRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.save(reqDTO, sessionUser);
        return "redirect:/";
    }

    // 게시글 수정
    @PostMapping("/api/boards/{id}")
    public String update(@PathVariable Integer id, BoardRequest.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.update(id, sessionUser.getId(), reqDTO);
        return "redirect:/board/" + id;
    }

    // 게시글 삭제
    @PostMapping("/api/boards/{id}")
    public String delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.delete(id, sessionUser.getId());
        return "redirect:/";
    }
}
