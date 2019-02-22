package com.cg.eshoppingzone.service;

import com.cg.eshoppingzone.model.User;
import com.cg.eshoppingzone.repository.RoleRepository;
import com.cg.eshoppingzone.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
  
    }

	@Override
	public User findByMobileNumber(Long mobilenumber) {
		return userRepository.findByMobilenumber(mobilenumber);
	}
}
