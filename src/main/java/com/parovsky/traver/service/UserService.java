package com.parovsky.traver.service;

import com.parovsky.traver.dto.UserDTO;
import com.parovsky.traver.exception.impl.UserIsAlreadyExistException;
import com.parovsky.traver.exception.impl.UserNotFoundException;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id) throws UserNotFoundException;

    UserDTO getUserByEmail(String email) throws UserNotFoundException;

    UserDTO saveUser(@NonNull UserDTO userDTO) throws UserIsAlreadyExistException;

    UserDTO updateUser(@NonNull UserDTO userDTO) throws UserNotFoundException;

    void deleteUser(Long id) throws UserNotFoundException;

    UserDTO getCurrentUser() throws UserNotFoundException;
}
