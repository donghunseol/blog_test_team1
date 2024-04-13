package shop.mtcoding.blog.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mtcoding.blog._core.errors.exception.Exception400;
import shop.mtcoding.blog._core.errors.exception.Exception401;
import shop.mtcoding.blog._core.errors.exception.Exception404;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserJPARepository userJPARepository;

    // 회원 가입
    @Transactional
    public void join(UserRequest.JoinDTO reqDTO){
        // id 중복 체크
        User user = userJPARepository.findByUsername(reqDTO.getUsername())
                .orElseThrow(() -> new Exception400("중복된 아이디 입니다"));

        // 중복 안되면 저장 (가입)
        userJPARepository.save(reqDTO.toEntity());
    }

    // 로그인
    public User login(UserRequest.LoginDTO reqDTO){
        User sessionUser = userJPARepository.findByUsernameAndPassword(reqDTO.getUsername(), reqDTO.getPassword())
                .orElseThrow(() -> new Exception401("인증 되지 않은 계정입니다"));
        return sessionUser;
    }

    // 회원 정보 수정
    @Transactional
    public User update(Integer id, UserRequest.UpdateDTO reqDTO){
        User sessionUser = userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("존재 하지 않는 계정입니다"));

        sessionUser.setPassword(reqDTO.getPassword());
        sessionUser.setEmail(reqDTO.getEmail());
        return sessionUser;
    }

    // 회원 정보 조회
    public User findByUser(Integer id){
        User user = userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("회원 정보를 찾을 수 없습니다"));
        return user;
    }
}
