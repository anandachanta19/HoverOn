package com.msp.hoveron.repository;

import com.msp.hoveron.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);
    boolean existsByEmail(String email);
    Users findByUserName(String username);

    Users findByUserId(int userId);

    Users findByUserNameAndPassword(String username, String password);
}
