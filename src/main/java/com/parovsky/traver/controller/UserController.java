package com.parovsky.traver.controller;

import com.parovsky.traver.dto.model.SaveUserModel;
import com.parovsky.traver.dto.model.UpdateUserModel;
import com.parovsky.traver.dto.view.UserView;
import com.parovsky.traver.exception.EntityAlreadyExistsException;
import com.parovsky.traver.exception.EntityNotFoundException;
import com.parovsky.traver.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class UserController {

	private final UserService userService;

	@ResponseBody
	@GetMapping("/users")
	public List<UserView> getUsers() {
		return userService.getAllUsers();
	}

	@ResponseBody
	@GetMapping("/user/{id}")
	public UserView getUserById(@PathVariable Long id) throws EntityNotFoundException {
		return userService.getUserById(id);
	}

	@ResponseBody
	@GetMapping("/current-user")
	public UserView getCurrentUser() throws EntityNotFoundException {
		return userService.getCurrentUser();
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/user", consumes = "application/json")
	public UserView saveUser(@Valid @RequestBody SaveUserModel model) throws EntityAlreadyExistsException {
		return userService.saveUserByAdmin(model);
	}

	@ResponseBody
	@PutMapping(value = "/user", consumes = "application/json")
	public UserView updateUser(@Valid @RequestBody UpdateUserModel model) throws EntityNotFoundException {
		return userService.updateUser(model);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/user/{id}")
	public void deleteUser(@PathVariable Long id) throws EntityNotFoundException {
		userService.deleteUser(id);
	}
}
