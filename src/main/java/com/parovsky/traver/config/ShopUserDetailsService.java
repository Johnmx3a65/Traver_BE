package com.parovsky.traver.config;

import com.parovsky.traver.dao.UserDAO;
import com.parovsky.traver.entity.User;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service("ShopUserDetailsService")
public class ShopUserDetailsService implements UserDetailsService {
    private final UserDAO userDAO;

    public ShopUserDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userDAO.getUserByEmail(email);
        return new UserPrincipal(user);
    }
}
