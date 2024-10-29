package com.dosol.securitydemo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class SampleController {

    @GetMapping("/")
    public String home(){
        log.info("home");
        return "index"; //어디가 인덱스였긴 했는데, 그게 어디더라
    }

    //@GetMapping("/user/login") config에서 설정해줘서 필요없나?
    public void login(){
        log.info("login");
    }

    @GetMapping("/all")
    public String exAll(){
        log.info("exAll");
        return "exAll";
    }


    //멤버나 어드민은 로그인 하지 않으면 못들어감. 그래서 주소 쳐도 그냥 터짐.
    //어드민은 당연히 어드민 로그인을 해야겠지.
    //권한 없는 페이지 가면 로그인으로 가는거임.
    //디테일하네.
    @GetMapping("/member")
    public void exMember(){
        log.info("exMember");
    }

    @GetMapping("/admin")
    public void exAdmin(){
        log.info("exAdmin");
    }
}
