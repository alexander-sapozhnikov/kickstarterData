package com.sapozhnikov.kickstarterData.repository;


import com.sapozhnikov.kickstarterData.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}