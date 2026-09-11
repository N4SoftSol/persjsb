package com.example.demo.dto;

public record JwtUserInfo(
        String username,
        String displayName,
        String email) {
}