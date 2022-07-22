package com.parovsky.traver.service.impl;

import com.parovsky.traver.dao.LocationDAO;
import com.parovsky.traver.dao.UserDAO;
import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.dto.UserDTO;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.entity.User;
import com.parovsky.traver.exception.impl.LocationNotFoundException;
import com.parovsky.traver.exception.impl.UserIsAlreadyExistException;
import com.parovsky.traver.exception.impl.UserNotFoundException;
import com.parovsky.traver.exception.impl.VerificationCodeNotMatchException;
import com.parovsky.traver.service.LocationService;
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

    private final LocationDAO locationDAO;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, LocationDAO locationDAO) {
        this.userDAO = userDAO;
        this.locationDAO = locationDAO;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userDAO.getAllUsers();
        return users.stream().map(UserService::transformUserToUserDTO).collect(Collectors.toList());
    }

    @Override
    public List<LocationDTO> getFavoriteLocations() throws UserNotFoundException {
        return locationDAO.getLocationsByUserId(getCurrentUser().getId()).stream().map(LocationService::transformLocationToLocationDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(@NonNull Long id) throws UserNotFoundException {
        User user = userDAO.getUserById(id);
        return UserService.transformUserToUserDTO(user);
    }

    @Override
    public UserDTO getUserByEmail(@NonNull String email) throws UserNotFoundException {
        User user = userDAO.getUserByEmail(email);
        return UserService.transformUserToUserDTO(user);
    }

    @Override
    public UserDTO getCurrentUserDTO() throws UserNotFoundException {
        User user = getCurrentUser();

        return UserService.transformUserToUserDTO(user);
    }

    @Override
    public UserDTO saveUser(@NonNull UserDTO userDTO) throws UserIsAlreadyExistException {
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        return UserService.transformUserToUserDTO(userDAO.saveUser(userDTO));
    }

    @Override
    public void addFavoriteLocation(Long locationId) throws UserNotFoundException, LocationNotFoundException {
        User user = getCurrentUser();
        Location location = locationDAO.getLocationById(locationId);
        userDAO.addFavouriteLocation(user.getId(), location.getId());
    }

    @Override
    public UserDTO updateUser(@NonNull UserDTO userDTO) throws UserNotFoundException {
        return UserService.transformUserToUserDTO(userDAO.updateUser(userDTO));
    }

    @Override
    public void resetPassword(UserDTO userDTO) throws UserNotFoundException, VerificationCodeNotMatchException {
        User user = userDAO.getUserByEmail(userDTO.getMail());
        if (user.getVerifyCode().equals(userDTO.getVerifyCode())) {
            user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            userDAO.updateUser(UserService.transformUserToUserDTO(user));
        }else {
            throw new VerificationCodeNotMatchException();
        }
    }

    @Override
    public void checkVerificationCode(UserDTO userDTO) throws UserNotFoundException, VerificationCodeNotMatchException {
        User user = userDAO.getUserByEmail(userDTO.getMail());
        if (!user.getVerifyCode().equals(userDTO.getVerifyCode())) {
            throw new VerificationCodeNotMatchException();
        }
    }

    @Override
    public void deleteUser(@NonNull Long id) throws UserNotFoundException {
        if (userDAO.isUserExist(id)) {
            userDAO.deleteUser(id);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void deleteFavoriteLocation(Long id) throws UserNotFoundException, LocationNotFoundException {
        User user = getCurrentUser();
        if (locationDAO.isLocationExist(id)) {
            userDAO.deleteFavouriteLocation(user.getId(), id);
        } else {
            throw new LocationNotFoundException();
        }
    }

    private User getCurrentUser() throws UserNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userEmail;

        if (principal instanceof UserDetails) {
            userEmail = ((UserDetails) principal).getUsername();
        } else {
            userEmail = principal.toString();
        }

        return userDAO.getUserByEmail(userEmail);
    }
}
