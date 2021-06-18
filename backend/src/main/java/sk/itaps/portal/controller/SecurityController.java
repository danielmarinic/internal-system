package sk.itaps.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.itaps.portal.domain.dto.CurrentUserDto;
import sk.itaps.portal.domain.dto.UserDto;
import sk.itaps.portal.domain.mapper.Mapper;
import sk.itaps.portal.service.UserService;

@RestController
public class SecurityController {
    @Autowired
    private Mapper mapper;

    @Autowired
    UserService userService;

    @GetMapping("/me")
    public CurrentUserDto user(@AuthenticationPrincipal OAuth2User principal) {
        return mapper.principalToCurrentUserDto(principal);
    }

    @GetMapping("/me/user")
    public UserDto userInfo(@AuthenticationPrincipal OAuth2User principal) {
        return mapper.userToUserDto(userService.getCurrentUser(principal));
    }
}
