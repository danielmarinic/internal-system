package sk.itaps.portal.service.impl;
/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import sk.itaps.portal.domain.jpa.User;
import sk.itaps.portal.repository.security.UserRepository;

//@Service
public class CustomOAuth2UserService extends OidcUserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public OidcUser loadUser(OidcUserRequest
                                       userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        User user = User.builder().build();
        user.setActive(true);
        user.setEmail("test");
        user.setObjectId("test");
        userRepository.save(user);
        return oidcUser;
    }
}
*/