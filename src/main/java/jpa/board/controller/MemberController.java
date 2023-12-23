package jpa.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {


    @GetMapping("/login")
    public String loginHome() {
        return "member/login";

    }
}
