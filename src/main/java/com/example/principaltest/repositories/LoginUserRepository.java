package com.example.principaltest.repositories;

import com.example.principaltest.models.LoginUser;
import com.example.principaltest.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginUserRepository extends JpaRepository<User, Long> {

    Optional<LoginUser> findByUsernameIgnoreCase(String username);

}