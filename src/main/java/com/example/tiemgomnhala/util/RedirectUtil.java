package com.example.tiemgomnhala.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class RedirectUtil {

    public static String getRedirectUrl(Authentication authentication) {
        // Stores granted authorities of the authenticated user into auth.
        // Note: This is not a MongoDB collection, but the Java collection class.

        Collection<? extends GrantedAuthority> auth = authentication.getAuthorities();

        // Loops through each of the authorities and redirect the user to their
        // respective home pages.

        for (GrantedAuthority grantedAuthority : auth) {

            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {

                return "/";

            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {

                return "/admin";

            }
        }
        return "/auth?error";
    }

}