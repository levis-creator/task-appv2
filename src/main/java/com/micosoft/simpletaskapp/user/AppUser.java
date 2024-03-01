package com.micosoft.simpletaskapp.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "app_user")
@AllArgsConstructor
@NoArgsConstructor
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID uuid;
    private String email;
    private String password;
    private  String username;
    @Enumerated(EnumType.STRING)
    @Column(name = "app_role")
    private AppRole appRole;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.appRole == AppRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("USER"));
        }
        return List.of(new SimpleGrantedAuthority("USER"));
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