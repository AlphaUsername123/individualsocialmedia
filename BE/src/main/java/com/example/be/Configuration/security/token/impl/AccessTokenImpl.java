package com.example.be.Configuration.security.token.impl;

import com.example.be.Configuration.security.token.AccessToken;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@EqualsAndHashCode
@Getter
public class AccessTokenImpl implements AccessToken {
    private final String subject;
    private final Long UserId;
    private final Set<String> roles;

    public AccessTokenImpl(String subject, Long UserId, Collection<String> roles) {
        this.subject = subject;
        this.UserId = UserId;
        this.roles = roles != null ? Set.copyOf(roles) : Collections.emptySet();
    }

    @Override
    public boolean hasRole(String roleName) {
        return this.roles.contains(roleName);
    }
}
