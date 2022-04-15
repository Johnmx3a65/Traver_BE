package com.parovsky.traver.service.impl;

import com.parovsky.traver.dao.UserDAO;
import com.parovsky.traver.dto.UserDTO;
import com.parovsky.traver.entity.User;
import com.parovsky.traver.exception.impl.UserIsAlreadyExistException;
import com.parovsky.traver.exception.impl.UserNotFoundException;
import com.parovsky.traver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userDAO.getAllUsers();
        return users.stream().map(this::transformUserToUserDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(@NonNull Long id) throws UserNotFoundException {
        User user = userDAO.getUserById(id);
        return transformUserToUserDTO(user);
    }

    @Override
    public UserDTO getUserByEmail(@NonNull String email) throws UserNotFoundException {
        User user = userDAO.getUserByEmail(email);
        return transformUserToUserDTO(user);
    }

    @Override
    public UserDTO getCurrentUser() throws UserNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userEmail;

        if (principal instanceof UserDetails) {
            userEmail = ((UserDetails) principal).getUsername();
        } else {
            userEmail = principal.toString();
        }

        User user = userDAO.getUserByEmail(userEmail);

        return transformUserToUserDTO(user);
    }

    @Override
    public UserDTO saveUser(@NonNull UserDTO userDTO) throws UserIsAlreadyExistException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        return transformUserToUserDTO(userDAO.saveUser(userDTO));
    }

    @Override
    public UserDTO updateUser(@NonNull UserDTO userDTO) throws UserNotFoundException {
        return transformUserToUserDTO(userDAO.updateUser(userDTO));
    }

    @Override
    public void deleteUser(@NonNull Long id) throws UserNotFoundException {
        if (userDAO.isUserExist(id)) {
            userDAO.deleteUser(id);
        } else {
            throw new UserNotFoundException();
        }
    }

    private UserDTO transformUserToUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getVerifyCode()
        );
    }
}
