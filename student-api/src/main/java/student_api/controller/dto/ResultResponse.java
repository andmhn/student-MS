package student_api.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultResponse {
	private Long id;

	@Schema(example = "user3@mycompany.com")
	@Email
	private String student_email;

	@Schema(example = "CS-50")
	private String paper_no;

	@Schema(example = "55.64")
	private Float marks;
}
