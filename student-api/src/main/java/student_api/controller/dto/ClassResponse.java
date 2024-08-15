package student_api.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClassResponse {

    @Schema(example = "1")
    @NotBlank
    private Long id;

    @Schema(example = "Physics class")
    @NotBlank
	private String name;
    
    @Schema(example = "Thermodynamics classes by Rahul Gupta")
    private String description;
    
    @Schema(example = "Offline")
	private String type;
    

    @Schema(example = "9:00")
    @NotBlank
	private String fromTime;

    @Schema(example = "11:30")
    @NotBlank
	private String toTime;
}
