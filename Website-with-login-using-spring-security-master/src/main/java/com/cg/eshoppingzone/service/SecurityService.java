package com.cg.eshoppingzone.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}
