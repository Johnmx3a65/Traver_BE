package com.parovsky.traver.controller;

import com.parovsky.traver.dto.form.SaveUserForm;
import com.parovsky.traver.dto.form.UpdateUserForm;
import com.parovsky.traver.dto.view.UserView;
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

	@GetMapping("/users")
	public List<UserView> getUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/user/{id}")
	public UserView getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/user", consumes = "application/json")
	public UserView saveUser(@Valid @RequestBody SaveUserForm model) {
		return userService.saveUserByAdmin(model);
	}

	@PutMapping(value = "/user", consumes = "application/json")
	public UserView updateUser(@Valid @RequestBody UpdateUserForm model) {
		return userService.updateUser(model);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/user/{id}")
	public void deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
	}
}
