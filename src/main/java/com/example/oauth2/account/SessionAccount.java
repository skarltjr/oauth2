package com.example.oauth2.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class SessionAccount implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionAccount(Account account) {
        this.name = account.getName();
        this.email = account.getEmail();
        this.picture = account.getPicture();
    }
}

