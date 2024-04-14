package shop.mtcoding.blog.reply;

import lombok.Data;

import java.sql.Timestamp;

public class ReplyResponse {
    @Data
    public static class DTO {
        private Integer id;
        private String content;
        private Integer userId;
        private Integer boardId;
        private Timestamp createdAt;

        public DTO(Reply reply) {
            this.id = reply.getId();
            this.content = reply.getComment();
            this.userId = reply.getUser().getId();
            this.boardId = reply.getBoard().getId();
            this.createdAt = reply.getCreatedAt();
        }
    }
}
