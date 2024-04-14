package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.descriptor.java.BooleanPrimitiveArrayJavaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog._core.util.ApiUtil;
import shop.mtcoding.blog.user.SessionUser;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;
    private final HttpSession session;

    // 글 목록 전체 조회
    @GetMapping("/")
    public ResponseEntity<?> index(){
        List<BoardResponse.MainDTO> respDTO = boardService.boardList();
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 글 상세 보기
    @GetMapping("/api/boards/{boardId}/detail")
    public ResponseEntity<?> detail(@PathVariable Integer boardId){
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        BoardResponse.DetailDTO respDTO = boardService.detail(boardId, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 글 조회
    @GetMapping("/api/boards/{boardId}")
    public ResponseEntity<?> findOne(@PathVariable Integer boardId){
        Board board = boardService.boardCheck(boardId);
        BoardResponse.DTO respDTO = new BoardResponse.DTO(board);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 게시글 작성
    @PostMapping("/api/boards")
    public ResponseEntity<?> save(@Valid @RequestBody BoardRequest.SaveDTO reqDTO, Errors errors) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        Board board = boardService.save(reqDTO, sessionUser);
        BoardResponse.DTO respDTO = new BoardResponse.DTO(board);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 게시글 수정
    @PutMapping("/api/boards/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody BoardRequest.UpdateDTO reqDTO, Errors errors) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        Board board = boardService.update(id, sessionUser.getId(), reqDTO);
        BoardResponse.DTO respDTO = new BoardResponse.DTO(board);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 게시글 삭제
    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        boardService.delete(id, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil<>(null));
    }
}
