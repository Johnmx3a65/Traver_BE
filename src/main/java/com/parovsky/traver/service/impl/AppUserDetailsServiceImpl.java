package com.parovsky.traver.service.impl;

import com.parovsky.traver.config.UserPrincipal;
import com.parovsky.traver.dao.UserDAO;
import com.parovsky.traver.entity.User;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("AppUserDetailsService")
public class AppUserDetailsServiceImpl implements UserDetailsService {
    private final UserDAO userDAO;

    public AppUserDetailsServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new UserPrincipal(user);
    }
}
