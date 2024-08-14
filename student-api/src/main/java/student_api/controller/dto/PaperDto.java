package student_api.controller.dto;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaperDto {

    @Schema(example = "CS-50")
    @Email
    private String name;

    @NotBlank
    private Date date;

    @Schema(example = "course")
    @NotBlank
    private String type;
}
