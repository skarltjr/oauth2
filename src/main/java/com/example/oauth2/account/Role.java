package com.example.oauth2.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUSET("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    //GUEST의 key는 ROLE_GUEST 타이틀은 손님
    private final String key;
    private final String title;
}
