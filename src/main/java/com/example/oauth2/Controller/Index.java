package com.example.oauth2.Controller;

import com.example.oauth2.account.AccountRepository;
import com.example.oauth2.account.LoginUser;
import com.example.oauth2.account.SessionAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class Index {

    private final HttpSession httpSession;
    private final AccountRepository accountRepository;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionAccount account) {

        if (account != null) {
            model.addAttribute("userName", account.getName());
        }

        System.out.println(accountRepository.count());
        return "index";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello(@LoginUser SessionAccount account) {
        if (account == null) {
            throw new AccessDeniedException("x");
        }
        return "hello";
    }
}