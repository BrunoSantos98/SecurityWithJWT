package com.securitywithjwt.SecurityWithJWT.models;

import com.securitywithjwt.SecurityWithJWT.models.enums.Roles;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_USER")
@Data
public class UserModel implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, length = 60)
    private String name;
    @Column(nullable = false, length = 30, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, length = 125, unique = true)
    private String email;
    @Column(nullable = false, length = 5)
    private Roles role;

    public UserModel() {
    }

    public UserModel(UUID id, String name, String username, String password, String email, Roles role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
