package com.example.be.Configuration.security.token;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
