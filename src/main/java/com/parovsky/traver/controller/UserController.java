package com.parovsky.traver.controller;

import com.parovsky.traver.dto.form.SaveUserForm;
import com.parovsky.traver.dto.form.UpdateUserForm;
import com.parovsky.traver.dto.response.UserResponse;
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
	public List<UserResponse> getUsers() {
		return userService.getAllUsers();
	}

	@ResponseBody
	@GetMapping("/user/{id}")
	public UserResponse getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/user", consumes = "application/json")
	public UserResponse saveUser(@Valid @RequestBody SaveUserForm model) {
		return userService.saveUserByAdmin(model);
	}

	@ResponseBody
	@PutMapping(value = "/user", consumes = "application/json")
	public UserResponse updateUser(@Valid @RequestBody UpdateUserForm model) {
		return userService.updateUser(model);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/user/{id}")
	public void deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
	}
}
