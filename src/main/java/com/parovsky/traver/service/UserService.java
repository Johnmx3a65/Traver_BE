package com.parovsky.traver.service;

import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.dto.UserDTO;
import com.parovsky.traver.entity.User;
import com.parovsky.traver.exception.impl.LocationNotFoundException;
import com.parovsky.traver.exception.impl.UserIsAlreadyExistException;
import com.parovsky.traver.exception.impl.UserNotFoundException;
import com.parovsky.traver.exception.impl.VerificationCodeNotMatchException;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    List<LocationDTO> getFavoriteLocations() throws UserNotFoundException;

    UserDTO getUserById(Long id) throws UserNotFoundException;

    UserDTO getUserByEmail(String email) throws UserNotFoundException;

    UserDTO saveUser(@NonNull UserDTO userDTO) throws UserIsAlreadyExistException;

    UserDTO updateUser(@NonNull UserDTO userDTO) throws UserNotFoundException;

    void deleteUser(Long id) throws UserNotFoundException;

    UserDTO getCurrentUserDTO() throws UserNotFoundException;

    void resetPassword(UserDTO userDTO) throws UserNotFoundException, VerificationCodeNotMatchException;

    void checkVerificationCode(UserDTO userDTO) throws UserNotFoundException, VerificationCodeNotMatchException;

    void deleteFavoriteLocation(Long id) throws UserNotFoundException, LocationNotFoundException;

    void addFavoriteLocation(Long locationId) throws UserNotFoundException, LocationNotFoundException;

     static UserDTO transformUserToUserDTO(User user) {
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
