package shop.mtcoding.blog.reply;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog._core.util.ApiUtil;
import shop.mtcoding.blog.user.User;

@RequiredArgsConstructor
@Controller
public class ReplyController {
    private final ReplyService replyService;
    private final HttpSession session;

    // 댓글 쓰기
    @PostMapping("/api/replies")
    public ResponseEntity<?> save(ReplyRequest.SaveDTO reqDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        replyService.save(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(null));
    }

    // 댓글 삭제
    @PostMapping("/api/replies/{replyId}")
    public ResponseEntity<?> delete(@PathVariable Integer replyId){
        User sessionUser = (User) session.getAttribute("sessionUser");
        Reply reply = replyService.delete(replyId, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil<>(reply));
    }
}