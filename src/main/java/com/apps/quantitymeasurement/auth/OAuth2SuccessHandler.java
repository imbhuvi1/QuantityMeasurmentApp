package com.apps.quantitymeasurement.auth;

import com.apps.quantitymeasurement.user.UserEntity;
import com.apps.quantitymeasurement.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public OAuth2SuccessHandler(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        if (!userService.existsByEmail(email)) {
            UserEntity newUser = new UserEntity(email, name, null, UserEntity.AuthProvider.GOOGLE);
            userService.save(newUser);
        }

        String token = jwtUtil.generateToken(email);
        String userName = userService.findByEmail(email).getName();
        String redirectUrl = "http://localhost:5500/index.html?token=" + token + "&name=" + java.net.URLEncoder.encode(userName, "UTF-8") + "&email=" + java.net.URLEncoder.encode(email, "UTF-8");
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
