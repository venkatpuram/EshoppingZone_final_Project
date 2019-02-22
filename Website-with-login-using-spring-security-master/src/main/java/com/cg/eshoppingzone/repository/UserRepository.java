package com.cg.eshoppingzone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.eshoppingzone.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

	User findByMobilenumber(Long mobilenumber);
}
