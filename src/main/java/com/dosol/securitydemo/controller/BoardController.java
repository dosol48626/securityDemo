package com.dosol.securitydemo.controller;

import com.dosol.securitydemo.domain.Board;
import com.dosol.securitydemo.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Log4j2
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("insert")
    public String insert() {
        return "/board/register";
    }

    @PostMapping("/insert")
    public String insert(Board board,
                         @AuthenticationPrincipal Board principal) {
        boardService.insert(board,principal.getUser() );
        return "redirect:/board/list";
        //이게 뭔가 하면, 프린시스디테일로 정보를 받고, 거기에 유저정보 롤정보 있기에 거기서 받겠다는거임
        //그래서 리다이렉트로 다시 보내는거네.
    }

    @GetMapping("/view")
    public String view(@RequestParam Long num, Model model) {
        model.addAttribute("board", boardService.findById(num));
        return "/board/view";
    }
    //수정폼
    @GetMapping("/modify")
    public String update(@RequestParam Long num, Model model) {
        model.addAttribute("board", boardService.findById(num));
        return "/board/modify";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("lists", boardService.list());
        return "/board/list";
    }
    @PutMapping("/update")
    public String update(Board board) {
        boardService.update(board);
        return "redirect:/board/view?num="+board.getNum();
    }
    //삭제
    @GetMapping("/delete")
    public String delete(@RequestParam Long num) {
        boardService.delete(num);
        return "redirect:/board/list";
    }

}
