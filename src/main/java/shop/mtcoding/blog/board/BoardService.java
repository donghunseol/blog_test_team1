package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardJPARepository boardJPARepository;

    // 게시글 조회
    public Board boardCheck(Integer boardId){
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다"));
        return board;
    }

    // 게시글 수정
    @Transactional
    public void update(Integer boardId, Integer sessionUserId, BoardRequest.UpdateDTO reqDTO){
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다"));

        // 권한 처리
        if(sessionUserId != board.getUser().getId()){
            throw new Exception403("게시글을 수정할 권한이 없습니다");
        }

        board.setTitle(reqDTO.getTitle());
        board.setContent(reqDTO.getContent());
    }

    // 게시글 쓰기
    @Transactional
    public void save(BoardRequest.SaveDTO reqDTO, User sessionUser){
        boardJPARepository.save(reqDTO.toEntity(sessionUser));
    }

    // 게시글 삭제
    @Transactional
    public void delete(Integer boardId, Integer sessionUserId) {
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다"));

        if(sessionUserId != board.getUser().getId()){
            throw new Exception403("게시글을 삭제할 권한이 없습니다");
        }

        boardJPARepository.deleteById(boardId);
    }

    // 게시글 목록 조회
    public List<Board> boardList() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id"); // 정렬 조회
        return boardJPARepository.findAll(sort);
    }

    // 게시글 상세 보기
    public Board detail(Integer boardId, User sessionUser) {
        Board board = boardJPARepository.findByJoinUser(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다"));

        boolean isOwner = false;
        if(sessionUser != null){
            if(sessionUser.getId() == board.getUser().getId()){
                isOwner = true;
            }
        }

        board.setOwner(isOwner);
        return board;
    }
}