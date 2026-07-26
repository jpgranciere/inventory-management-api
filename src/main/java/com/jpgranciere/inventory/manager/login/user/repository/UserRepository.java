package com.jpgranciere.inventory.manager.login.user.repository;

import com.jpgranciere.inventory.manager.login.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional <UserDetails> findByLogin(String login);
}
