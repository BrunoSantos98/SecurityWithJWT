package com.securitywithjwt.SecurityWithJWT.repository;

import com.securitywithjwt.SecurityWithJWT.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    UserDetails findByUsername(String username); //Testando com userDetails desta vez
    UserModel findByEmail(String Email);
}
