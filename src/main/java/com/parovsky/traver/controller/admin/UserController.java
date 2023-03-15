package com.parovsky.traver.controller.admin;

import com.parovsky.traver.dto.UserDTO;
import com.parovsky.traver.dto.UserResponse;
import com.parovsky.traver.exception.impl.UserIsAlreadyExistException;
import com.parovsky.traver.exception.impl.UserNotFoundException;
import com.parovsky.traver.service.UserService;
import com.parovsky.traver.utils.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.parovsky.traver.utils.ModelMapper.mapUserDTO;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/users")
	public ResponseEntity<List<UserResponse>> getUsers() {
		List<UserDTO> users = userService.getAllUsers();
		List<UserResponse> response = users
				.stream()
				.map(ModelMapper::mapUserDTO)
				.collect(Collectors.toList());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/user")
	public ResponseEntity<UserResponse> getUser(@RequestParam Map<String, String> params) throws UserNotFoundException {
		if (params.containsKey("email")) {
			String email = params.get("email");
			if (userService.isUserExistByEmail(email)) {
				UserDTO userDTO = userService.getUserByEmail(email);
				return new ResponseEntity<>(mapUserDTO(userDTO), HttpStatus.OK);
			} else {
				throw new UserNotFoundException();
			}
		} else if (params.containsKey("id")) {
			Long id = Long.parseLong(params.get("id"));
			if (userService.isUserExist(id)) {
				UserDTO userDTO = userService.getUserById(id);
				return new ResponseEntity<>(mapUserDTO(userDTO), HttpStatus.OK);
			} else {
				throw new UserNotFoundException();
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/current-user")
	public ResponseEntity<UserResponse> getCurrentUser() throws UserNotFoundException {
		String userEmail = userService.getCurrentUserEmail();
		if (userService.isUserExistByEmail(userEmail)) {
			UserDTO userDTO = userService.getUserByEmail(userEmail);
			return new ResponseEntity<>(mapUserDTO(userDTO), HttpStatus.OK);
		} else {
			throw new UserNotFoundException();
		}
	}

	@PostMapping(value = "/user", consumes = "application/json")
	public ResponseEntity<UserResponse> saveUser(@RequestBody UserDTO userDTO) throws UserIsAlreadyExistException {
		if (!userService.isUserExistByEmail(userDTO.getEmail())) {
			UserDTO user = userService.saveUser(userDTO);
			return new ResponseEntity<>(mapUserDTO(user), HttpStatus.CREATED);
		} else {
			throw new UserIsAlreadyExistException();
		}
	}

	@PutMapping(value = "/user", consumes = "application/json")
	public ResponseEntity<UserResponse> updateUser(@RequestBody UserDTO userDTO) throws UserNotFoundException {
		if (userService.isUserExist(userDTO.getId())){
			UserDTO user = userService.updateUser(userDTO);
			return new ResponseEntity<>(mapUserDTO(user), HttpStatus.OK);
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
