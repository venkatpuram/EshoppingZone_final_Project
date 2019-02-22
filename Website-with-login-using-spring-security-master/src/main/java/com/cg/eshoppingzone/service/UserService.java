package com.cg.eshoppingzone.service;

import com.cg.eshoppingzone.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
    
    User findByMobileNumber(Long mobilenumber);
}
