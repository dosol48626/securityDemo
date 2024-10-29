package com.dosol.securitydemo.controller;

import com.dosol.securitydemo.domain.User;
import com.dosol.securitydemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;  //시큐리티컨피그에 있던 애임. 이걸 주입 받겠다.

    @GetMapping("")
    public String index(){
        return "index";
    }

    @GetMapping("/join")
    public void join(){
    }

    @PostMapping("register")
    public String register(User user) {
        System.out.println("회원가입 진행 : " + user);
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword); //패스워드 암호화
        user.setRole("USER"); //Role이 뭐냐하면, 회원가입할때, 롤에 따라 제한을 둘건데, USER라는 롤을 주면,
        //일반 유저가 접근하는거 다 허용하는거고, 대문자 어드민을 해주면 어드민 사용자
        userRepository.save(user);
        return "redirect:/user/login"; //회원가입 끝나면 로그인으로 가기로 함. 우린 그 컨트롤러 뷰를 만든 적이 없음
        //그건 시큐리티 컨피그에서 설정했던거임.
    }

    @GetMapping("login")
    public void login(){

    }
}
