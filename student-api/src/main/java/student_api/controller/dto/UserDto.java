package student_api.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	Long id;

	@Schema(example = "user3")
	String username;

	@Schema(example = "User3")
	String name;

	@Schema(example = "user3@mycompany.com")
	@Email
	String email;

	@Schema(example = "USER")
	String role;
}