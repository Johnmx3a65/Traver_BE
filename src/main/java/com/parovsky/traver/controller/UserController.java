package com.parovsky.traver.controller;

import com.parovsky.traver.dto.UserDTO;
import com.parovsky.traver.exception.impl.UserNotFoundException;
import com.parovsky.traver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getUsers() {
		List<UserDTO> users = userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/user")
	public ResponseEntity<UserDTO> getUser(@RequestParam Map<String, String> params) throws UserNotFoundException {
		if (params.containsKey("email")) {
			String email = params.get("email");
			if (userService.isUserExistByEmail(email)) {
				UserDTO userDTO = userService.getUserByEmail(email);
				return new ResponseEntity<>(userDTO, HttpStatus.OK);
			} else {
				throw new UserNotFoundException();
			}
		}else if (params.containsKey("id")) {
			Long id = Long.parseLong(params.get("id"));
			if (userService.isUserExist(id)) {
				UserDTO userDTO = userService.getUserById(id);
				return new ResponseEntity<>(userDTO, HttpStatus.OK);
			} else {
				throw new UserNotFoundException();
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/current-user")
	public ResponseEntity<UserDTO> getCurrentUser() throws UserNotFoundException {
		String userEmail = userService.getCurrentUserEmail();
		if (userService.isUserExistByEmail(userEmail)) {
			UserDTO userDTO = userService.getUserByEmail(userEmail);
			return new ResponseEntity<>(userDTO, HttpStatus.OK);
		} else {
			throw new UserNotFoundException();
		}
	}

	@PutMapping(value = "/user", consumes = "application/json")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) throws UserNotFoundException {
		if (userService.isUserExist(userDTO.getId())){
			UserDTO user = userService.updateUser(userDTO);
			return new ResponseEntity<>(user, HttpStatus.OK);
		}else {
			throw new UserNotFoundException();
		}
	}

	@DeleteMapping(value = "/user/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws UserNotFoundException {
		if (userService.isUserExist(id)) {
			userService.deleteUser(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}else {
			throw new UserNotFoundException();
		}
	}
}
