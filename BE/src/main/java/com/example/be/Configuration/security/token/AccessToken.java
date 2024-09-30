package com.example.be.Configuration.security.token;

import java.util.Set;

public interface AccessToken {
    String getSubject();

    Long getCustomerId();

    Set<String> getRoles();

    boolean hasRole(String roleName);
}
