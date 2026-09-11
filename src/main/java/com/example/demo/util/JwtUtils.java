package com.example.demo.util;

import com.example.demo.dto.JwtUserInfo;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public final class JwtUtils {

    private JwtUtils() {
    }

    public static JwtUserInfo getUserInfo(
            Authentication authentication) {

        JwtAuthenticationToken jwt =
                (JwtAuthenticationToken)
                        authentication;

        return new JwtUserInfo(

                jwt.getName(),

                jwt.getToken()
                        .getClaimAsString(
                                "displayName"),

                jwt.getToken()
                        .getClaimAsString(
                                "email"));
    }
}