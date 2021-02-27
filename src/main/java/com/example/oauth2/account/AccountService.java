package com.example.oauth2.account;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final AccountRepository accountRepository;
    private final HttpSession httpSession;


    private Account saveOrUpdate(OAuthAttributes attributes) {
        Account byEmail = accountRepository.findByEmail(attributes.getEmail());
        if (byEmail != null) {
            byEmail.update(attributes.getName(), attributes.getPicture());
        } else {
            Account build = Account.builder()
                    .email(attributes.getEmail())
                    .name(attributes.getName())
                    .picture(attributes.getPicture())
                    .role(Role.GUSET)
                    .build();
            return accountRepository.save(build);
        }
        return accountRepository.save(byEmail);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User user = delegate.loadUser(userRequest);

        String clientId = userRequest.getClientRegistration().getClientId();
        String username = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.
                of(clientId, username, user.getAttributes());

        Account account = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionAccount(account));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(account.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }
}
