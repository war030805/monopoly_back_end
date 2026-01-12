package warre.me.backend;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import warre.me.backend.game.domain.gamePlayer.PlayerId;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@Component
public class TestHelper {

    public RequestPostProcessor getKeySecured(PlayerId userId) {
        return jwt().authorities(List.of(new SimpleGrantedAuthority("user"),
                        new SimpleGrantedAuthority("owner")))
                .jwt(jwt -> jwt
                        .claim(StandardClaimNames.SUB, userId.id().toString())
                        .claim(StandardClaimNames.GIVEN_NAME, "givenName")
                        .claim(StandardClaimNames.FAMILY_NAME, "familyName")
                        .claim(StandardClaimNames.EMAIL, "email")
                );
    }


}
