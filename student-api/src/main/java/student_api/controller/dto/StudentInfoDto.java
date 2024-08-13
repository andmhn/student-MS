package student_api.controller.dto;

public record StudentInfoDto(
		Long id,
		String first_name,
		String last_name,
		String email
		) {

}
