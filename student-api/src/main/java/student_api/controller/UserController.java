package student_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import student_api.controller.dto.UserDto;
import student_api.mapper.UserMapper;
import student_api.model.User;
import student_api.security.CustomUserDetails;
import student_api.service.UserService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.stream.Collectors;

import static student_api.config.SwaggerConfig.BASIC_AUTH_SECURITY_SCHEME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;
	private final UserMapper userMapper;

	@Operation(summary = "Get current user		*USER", security = { @SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME) })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content) })
	@GetMapping("/me")
	public UserDto getCurrentUser(@AuthenticationPrincipal CustomUserDetails currentUser) {
		return userMapper.toUserDto(userService.validateAndGetUserByUsername(currentUser.getUsername()));
	}

	@Operation(summary = "Get all users		*ADMIN", security = { @SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME) })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found users"),
			@ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content) })
	@GetMapping
	public List<UserDto> getUsers() {
		return userService.getUsers().stream().map(userMapper::toUserDto).collect(Collectors.toList());
	}

	@Operation(summary = "Get a user by its username	*ADMIN", security = {
			@SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME) })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the user"),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content) })
	@GetMapping("/{username}")
	public UserDto getUser(@PathVariable String username) {
		return userMapper.toUserDto(userService.validateAndGetUserByUsername(username));
	}

	@Operation(summary = "Delete user by username	*ADMIN", security = { @SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME) })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "DELETED user"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content) })
	@DeleteMapping("/{username}")
	public UserDto deleteUser(@PathVariable String username) {
		User user = userService.validateAndGetUserByUsername(username);
		userService.deleteUser(user);
		return userMapper.toUserDto(user);
	}
}
