package student_api.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResultRequest {

    @Schema(example = "user3@mycompany.com")
    @Email
    private String student_email;

    @Schema(example = "CS-101")
    @NotBlank
    private String paper_id;
    

    @Schema(example = "55.64")
    @NotBlank
    private Float marks_obtained;
}
