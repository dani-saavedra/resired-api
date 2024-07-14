package com.resired.api.security.infraestructure.authorization;

import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import com.resired.api.security.infraestructure.sql.orm.UserRolOrm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails extends UserOrm implements UserDetails {

    private final String email;
    private final String password;
    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(UserOrm user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.authorities = user.getUserRols()
            .stream()
            .filter(UserRolOrm::isActive)
            .map(userRolOrm -> new SimpleGrantedAuthority(userRolOrm.getRol().name()))
            .toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
