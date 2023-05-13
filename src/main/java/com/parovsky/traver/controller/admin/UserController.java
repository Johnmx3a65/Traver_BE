package com.parovsky.traver.controller.admin;

import com.parovsky.traver.dto.model.UserModel;
import com.parovsky.traver.dto.view.UserView;
import com.parovsky.traver.exception.impl.UserIsAlreadyExistException;
import com.parovsky.traver.exception.impl.UserNotFoundException;
import com.parovsky.traver.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
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
	public UserView getUserById(@PathVariable Long id) throws UserNotFoundException {
		return userService.getUserById(id);
	}

	//todo spring doesn't see the difference between /user/1 and /user/ivan@gmail.com
	/*@ResponseBody
	@GetMapping("/user/{email}")
	public UserView getUserByEmail(@PathVariable String email) throws UserNotFoundException {
		return userService.getUserByEmail(email);
	}*/

	@ResponseBody
	@GetMapping("/current-user")
	public UserView getCurrentUser() {
		return userService.getCurrentUser();
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/user", consumes = "application/json")
	public UserView saveUser(@RequestBody UserModel userModel) throws UserIsAlreadyExistException {
		return userService.saveUserByAdmin(userModel);
	}

	@ResponseBody
	@PutMapping(value = "/user", consumes = "application/json")
	public UserView updateUser(@RequestBody UserModel userModel) throws UserNotFoundException {
		return userService.updateUser(userModel);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/user/{id}")
	public void deleteUser(@PathVariable Long id) throws UserNotFoundException {
		userService.deleteUser(id);
	}
}
