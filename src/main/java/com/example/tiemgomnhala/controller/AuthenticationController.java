package com.example.tiemgomnhala.controller;

import com.example.tiemgomnhala.util.RedirectUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;

public class AuthenticationController {
    @GetMapping({"/login"})
    public String loginView(Authentication authentication) {
        String redirect = getAuthenticatedUserRedirectUrl(authentication);
        if (redirect != null) {
            return redirect;
        }
        return "redirect:/home";
    }

    @GetMapping({"/logout"})
    public String logout() {
        return "redirect:/login";
    }

    private String getAuthenticatedUserRedirectUrl(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String redirectUrl = RedirectUtil.getRedirectUrl(authentication);
            return "redirect:" + redirectUrl;
        }
        return null;
    }
}
