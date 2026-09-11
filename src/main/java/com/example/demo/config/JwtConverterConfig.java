package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class JwtConverterConfig {

    @Bean
    Converter<Jwt, AbstractAuthenticationToken>
    jwtAuthenticationConverter() {

        return jwt -> {
/*
            Set<GrantedAuthority> authorities =
                    new HashSet<>();

            List<String> scopes =
                    jwt.getClaimAsStringList("scope");

            if (scopes != null) {

                scopes.forEach(scope ->
                        authorities.add(
                                new SimpleGrantedAuthority(
                                        "SCOPE_" + scope)));
            }

            List<String> ldapScopes =
                    jwt.getClaimAsStringList(
                            "ldapScopes");

            if (ldapScopes != null) {

                ldapScopes.forEach(scope ->
                        authorities.add(
                                new SimpleGrantedAuthority(
                                        "SCOPE_" + scope)));
            }
*/

            Set<GrantedAuthority> authorities =
                    new HashSet<>();

            List<String> scopes =
                    jwt.getClaimAsStringList(
                            "ldapScopes");

            if (scopes != null) {

                scopes.forEach(scope ->
                        authorities.add(
                                new SimpleGrantedAuthority(
                                        "SCOPE_" + scope)));
            }


//            System.out.println(
//                    "LDAP Scopes = " + scopes);
//
//            System.out.println(
//                    "Authorities = " + authorities);

            return new JwtAuthenticationToken(
                    jwt,
                    authorities);
        };

    }
}