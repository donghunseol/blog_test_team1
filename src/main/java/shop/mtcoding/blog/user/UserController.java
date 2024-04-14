package shop.mtcoding.blog.user;

import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.errors.exception.Exception400;
import shop.mtcoding.blog._core.errors.exception.Exception401;
import shop.mtcoding.blog._core.util.ApiUtil;
import shop.mtcoding.blog._core.util.JwtUtil;


@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    // 회원 가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody UserRequest.JoinDTO reqDTO, Errors errors) {
        User user = userService.join(reqDTO);
        UserResponse.DTO respDTO = userService.findByUser(user.getId());
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequest.LoginDTO reqDTO, Errors errors) {
        String jwt = userService.login(reqDTO);
        UserResponse.LoginDTO respDTO = userService.findByLoginUser(JwtUtil.verify(jwt).getId());
        return ResponseEntity.ok().header("Authorization", "Bearer " + jwt).body(new ApiUtil<>(respDTO));
    }

    // 회원 정보 수정
    @PutMapping("/api/users/{userId}")
    public ResponseEntity<?> update(@PathVariable Integer userId, @Valid @RequestBody UserRequest.UpdateDTO reqDTO, Errors errors) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        SessionUser newSessionUser = userService.update(sessionUser.getId(), reqDTO);
        session.setAttribute("sessionUser", newSessionUser);
        UserResponse.DTO respDTO = userService.findByUser(newSessionUser.getId());
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 회원 정보 출력
    @GetMapping("/api/users/{userId}")
    public ResponseEntity<?> userinfo(@PathVariable Integer userId) {
        UserResponse.DTO respDTO = userService.findByUser(userId);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        session.invalidate();
        return ResponseEntity.ok(new ApiUtil<>(null));
    }
}
