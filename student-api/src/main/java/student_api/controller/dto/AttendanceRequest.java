package student_api.controller.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttendanceRequest {

	@Schema(example = "user3@mycompany.com")
	@Email
    @NotBlank
	private String student_email;

	@Schema(example = "1")
	@NotBlank
	private Long class_id;

	@NotBlank
	private LocalDateTime date;

	@Schema(example = "false")
	@NotBlank
	private Boolean is_present;

	@Schema(example = "Sickness")
	private String absent_reason;
}
