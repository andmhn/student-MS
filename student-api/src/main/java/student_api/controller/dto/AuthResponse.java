package student_api.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
	private Long id;

	@Schema(example = "User3")
	private String name;

	@Schema(example = "USER")
	private String role;
}