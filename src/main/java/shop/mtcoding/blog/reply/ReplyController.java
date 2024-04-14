package shop.mtcoding.blog.reply;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.util.ApiUtil;
import shop.mtcoding.blog.user.SessionUser;

@RequiredArgsConstructor
@RestController
public class ReplyController {
    private final ReplyService replyService;
    private final HttpSession session;

    // 댓글 쓰기
    @PostMapping("/api/replies")
    public ResponseEntity<?> save(@Valid @RequestBody ReplyRequest.SaveDTO reqDTO, Errors errors){
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        ReplyResponse.DTO respDTO = replyService.save(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 댓글 삭제
    @DeleteMapping("/api/replies/{replyId}")
    public ResponseEntity<?> delete(@PathVariable Integer replyId){
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        replyService.delete(replyId, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil<>(null));
    }
}