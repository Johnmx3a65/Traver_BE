package com.parovsky.traver.dao.impl;

import com.parovsky.traver.dao.LocationDAO;
import com.parovsky.traver.dao.UserDAO;
import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.dto.UserDTO;
import com.parovsky.traver.entity.User;
import com.parovsky.traver.exception.impl.UserIsAlreadyExistException;
import com.parovsky.traver.exception.impl.UserNotFoundException;
import com.parovsky.traver.repository.UserRepository;
import com.parovsky.traver.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class UserDAOImpl implements UserDAO {
    private final UserRepository userRepository;

    @Autowired
    public UserDAOImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public boolean isUserExist(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public User saveUser(@NonNull UserDTO userDTO) throws UserIsAlreadyExistException {
        if (userRepository.existsUserByEmail(userDTO.getMail())) {
            throw new UserIsAlreadyExistException();
        } else {
            User user = new User();
            user.setEmail(userDTO.getMail());
            user.setName(userDTO.getName());
            user.setPassword(userDTO.getPassword());
            user.setRole(userDTO.getRole().equals(Role.ADMIN.name()) ? Role.ADMIN.name() : Role.USER.name());
            userRepository.saveAndFlush(user);
            return user;
        }
    }

    @Override
    public User updateUser(@NonNull UserDTO userDTO) throws UserNotFoundException {
        User user = userRepository.findById(userDTO.getId()).orElseThrow(UserNotFoundException::new);
        user.setEmail(userDTO.getMail());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setRole(userDTO.getRole());
        user.setVerifyCode(userDTO.getVerifyCode());
        user.setFavouriteLocations(userDTO.getFavoriteLocations().stream().map(LocationDAOImpl::transformLocationDTO).collect(Collectors.toList()));
        userRepository.saveAndFlush(user);
        return user;
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        if (userRepository.deleteAllById(id) == 0) {
            throw new UserNotFoundException();
        }
    }
}
