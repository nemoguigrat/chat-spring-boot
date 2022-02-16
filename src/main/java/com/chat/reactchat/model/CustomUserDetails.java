package com.chat.reactchat.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class CustomUserDetails implements UserDetails {
    private String email;
    private String password;
    private Boolean active;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static CustomUserDetails fromUserEntityToCustomUserDetails(User userEntity) {
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setEmail(userEntity.getEmail());
        customUserDetails.setPassword(userEntity.getPassword());
        customUserDetails.setActive(userEntity.getActive());
        customUserDetails.setGrantedAuthorities(userEntity.getRole());
        return customUserDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
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
        return active;
    }
}
