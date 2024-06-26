package shop.mtcoding.blog.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mtcoding.blog._core.errors.exception.Exception400;
import shop.mtcoding.blog._core.errors.exception.Exception401;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog._core.util.JwtUtil;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserJPARepository userJPARepository;

    // 회원 가입
    @Transactional
    public User join(UserRequest.JoinDTO reqDTO){
        // id 중복 체크
        Optional<User> user = userJPARepository.findByUsername(reqDTO.getUsername());

        if(user.isPresent()){
            throw new Exception400("중복된 아이디 입니다");
        }

        // 중복 안되면 저장 (가입)
        return userJPARepository.save(reqDTO.toEntity());
    }

    // 로그인
    public String login(UserRequest.LoginDTO reqDTO){
        User sessionUser = userJPARepository.findByUsernameAndPassword(reqDTO.getUsername(), reqDTO.getPassword())
                .orElseThrow(() -> new Exception401("인증 되지 않은 계정입니다"));
        String jwt = JwtUtil.create(sessionUser);
        return jwt;
    }

    // 회원 정보 수정
    @Transactional
    public SessionUser update(Integer id, UserRequest.UpdateDTO reqDTO){

        User sessionUser = userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("존재 하지 않는 계정입니다"));

        sessionUser.setPassword(reqDTO.getPassword());
        sessionUser.setEmail(reqDTO.getEmail());
        return new SessionUser(sessionUser);
    }

    // 회원 정보 조회
    public UserResponse.DTO findByUser(Integer id){
        User user = userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("회원 정보를 찾을 수 없습니다"));
        return new UserResponse.DTO(user);
    }

    // 로그인 회원 정보 조회
    public UserResponse.LoginDTO findByLoginUser(Integer id){
        User user = userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("회원 정보를 찾을 수 없습니다"));
        return new UserResponse.LoginDTO(user);
    }
}
