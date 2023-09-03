package com.parovsky.traver.service.impl;

import com.parovsky.traver.entity.User;
import com.parovsky.traver.exception.ApplicationException;
import com.parovsky.traver.repository.UserRepository;
import com.parovsky.traver.security.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.parovsky.traver.exception.Errors.BAD_CREDENTIALS;

@Service("AppUserDetailsService")
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class AppUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ApplicationException(BAD_CREDENTIALS));
        return new UserPrincipal(user);
    }
}
