package student_api.controller.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResponse {
	private Long id;
	
    @Schema(example = "user3@mycompany.com")
    @Email
    @NotBlank
	private String student_email;

    private String class_name;
    
    @NotBlank
	private Long class_id;
	
    @NotBlank
	private LocalDate date;

	@Schema(example = "false")
    @NotBlank
	private Boolean is_present;

    @Schema(example = "Sickness")
	private String absent_reason;
}
