package com.example.oauth2.Controller;

import com.example.oauth2.account.AccountRepository;
import com.example.oauth2.account.LoginUser;
import com.example.oauth2.account.SessionAccount;
import com.example.oauth2.events.EventController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequiredArgsConstructor
public class Index {

    private final HttpSession httpSession;
    private final AccountRepository accountRepository;


    @GetMapping("/")
    @ResponseBody
    public String first() {
        return "hello";
    }

    /**
     * - 즉 로그인은 구글로그인을 통해 + 세션에 정보저장 + 로그인하기는 href="/oauth2/authorization/google"링크로
     * 그러면 첫화면은 permitall인데 만약 로그인을 했다면 @LoginUser SessionAccount account account가 null이 아닐테고
     * 그럼 이때는 다음 상황으로 전이가능한 링크를 추가하여 리턴
     * - 만약 로그인하지않은. account가 null이라면 로그인하기 링크를 추가
     */
    @GetMapping("/index")
    public String index(Model model, @LoginUser SessionAccount account) {

        if (account != null) {
            model.addAttribute("userName", account.getName());
        }
        return "index";
    }
    @GetMapping("/api")
    @ResponseBody
    public RepresentationModel firstPage() {
        var index = new RepresentationModel<>();
        index.add(linkTo(EventController.class).withRel("events"));
        return index;
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