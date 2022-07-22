package com.parovsky.traver.dao;

import com.parovsky.traver.dto.UserDTO;
import com.parovsky.traver.entity.User;
import com.parovsky.traver.exception.impl.UserIsAlreadyExistException;
import com.parovsky.traver.exception.impl.UserNotFoundException;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();

    User getUserById(Long id) throws UserNotFoundException;

    User getUserByEmail(String email) throws UserNotFoundException;

    boolean isUserExist(Long id);

    User saveUser(@NonNull UserDTO userDTO) throws UserIsAlreadyExistException;

    User updateUser(@NonNull UserDTO userDTO) throws UserNotFoundException;

    void deleteUser(Long id) throws UserNotFoundException;

    void addFavouriteLocation(Long userId, Long locationId);

    void deleteFavouriteLocation(Long userId, Long locationId);
}
