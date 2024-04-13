package shop.mtcoding.blog.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import shop.mtcoding.blog.reply.Reply;
import shop.mtcoding.blog.user.User;

import java.util.ArrayList;
import java.util.List;

public class BoardResponse {

    // 게시글 조회
    @Data
    public static class MainDTO{
        private Integer id;
        private String title;

        public MainDTO(Board board){
            this.id = board.getId();
            this.title = board.getTitle();
        }
    }

    // 게시글 상세 보기
    @Data
    public static class DetailDTO{
        private Integer id;
        private String title;
        private String content;
        private Integer userId;
        private String username;
        private boolean isOwner;
        private List<ReplyDTO> replies = new ArrayList<>();

        public DetailDTO(Board board, User sessionUser){
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.userId = board.getUser().getId();
            this.username = board.getUser().getUsername();
            this.isOwner = false;
            if(sessionUser!= null){
                if(sessionUser.getId() == userId){
                    this.isOwner = true;
                }
            }
            this.replies = board.getReplies().stream().map(reply -> new ReplyDTO(reply, sessionUser)).toList();
        }

        @Data
        public class ReplyDTO {
            private Integer id;
            private String comment;
            private Integer userId; // 댓글 작성자 아이디
            private String username; // 댓글 작성자 이름
            private boolean isOwner;

            public ReplyDTO(Reply reply, User sessionUser) {
                this.id = reply.getId();
                this.comment = reply.getComment();
                this.userId = reply.getUser().getId();
                this.username = reply.getUser().getUsername();
                this.isOwner = false;
                if(sessionUser != null){
                    if(sessionUser.getId() == userId) isOwner = true;
                }
            }
        }
    }
}
