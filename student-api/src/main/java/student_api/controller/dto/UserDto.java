package student_api.controller.dto;

public record UserDto(Long id, String username, String name, String email, String role) {
}