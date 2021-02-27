package com.example.oauth2.account;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
    // public String index(Model model, @LoginUser SessionAccount account)처럼 사용할 때
    // LoginUserArgumentResolver에서 parameter.getParameterAnnotation(LoginUser.class) != null;로
    // 파라미터로 넘어온 SessionAccount가 널이 아니라면 인증된 객체가 들어있음을 판단.
}
