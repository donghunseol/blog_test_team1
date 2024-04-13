package shop.mtcoding.blog.reply;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog.user.User;

@RequiredArgsConstructor
@Controller
public class ReplyController {
    private final ReplyService replyService;
    private final HttpSession session;

    // 댓글 쓰기
    @PostMapping("/api/replies")
    public String save(ReplyRequest.SaveDTO reqDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        replyService.save(reqDTO, sessionUser);
        return "redirect:/board/"+reqDTO.getBoardId();
    }

    // 댓글 삭제
    @PostMapping("/api/replies/{replyId}")
    public String delete(@PathVariable Integer replyId){
        User sessionUser = (User) session.getAttribute("sessionUser");
        replyService.delete(replyId, sessionUser.getId());
        return "redirect:/board";
    }
}