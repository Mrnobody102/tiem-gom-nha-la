/**
 * This class allows users to be redirected to their respective home (or admin panel) after successful authentication.
 */
package com.example.tiemgomnhala.config.security;

import com.example.tiemgomnhala.util.RedirectUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {


        String redirectUrl = RedirectUtil.getRedirectUrl(authentication);


        if (redirectUrl == null) {
            throw new IllegalStateException("Could not determine user role for redirection");
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);

    }
}