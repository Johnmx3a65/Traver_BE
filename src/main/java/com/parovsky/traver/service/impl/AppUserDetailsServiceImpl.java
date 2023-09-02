package com.parovsky.traver.service.impl;

import com.parovsky.traver.config.UserPrincipal;
import com.parovsky.traver.dao.UserDao;
import com.parovsky.traver.entity.User;
import com.parovsky.traver.exception.ApplicationException;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.parovsky.traver.exception.Errors.BAD_CREDENTIALS;

@Service("AppUserDetailsService")
public class AppUserDetailsServiceImpl implements UserDetailsService {
    private final UserDao userDAO;

    public AppUserDetailsServiceImpl(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userDAO.getByEmail(email).orElseThrow(() -> new ApplicationException(BAD_CREDENTIALS));
        return new UserPrincipal(user);
    }
}
