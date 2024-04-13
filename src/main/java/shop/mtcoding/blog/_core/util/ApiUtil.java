package shop.mtcoding.blog._core.util;

import lombok.Data;

@Data
public class ApiUtil<T> {

    private Integer status;
    private String message;
    private T body;

    public ApiUtil(T body){
        this.status = 200;
        this.message = "성공";
        this.body = body;
    }

    public ApiUtil(Integer status, String message){
        this.status = status;
        this.message = message;
        this.body = null;
    }
}
