package com.example.demo.config;

import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class ResourceServerJwtConfig {

    @Value("${jwt.keystore.location}")
    private String keystoreLocation;

    @Value("${jwt.keystore.password:NOT_FOUND}")
    private String keystorePassword;

    @Value("${jwt.key.alias:NOT_FOUND}")
    private String keyAlias;

    @Value("${jwt.key.password:NOT_FOUND}")
    private String keyPassword;

    @Bean
    JwtDecoder jwtDecoder() throws Exception {

        KeyStore keyStore = KeyStore.getInstance("PKCS12");

        try (InputStream is = new ClassPathResource(keystoreLocation).getInputStream()) {

            keyStore.load(is, keystorePassword.toCharArray());
        }

        RSAKey rsaKey = RSAKey.load(keyStore, keyAlias, keyPassword.toCharArray());

        RSAPublicKey publicKey = rsaKey.toRSAPublicKey();

        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }
}