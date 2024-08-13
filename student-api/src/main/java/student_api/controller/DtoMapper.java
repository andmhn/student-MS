package student_api.controller;

import student_api.controller.dto.StudentInfoDto;
import student_api.model.Student;

public class DtoMapper {
	public static StudentInfoDto toStudentInfoDto(Student student) {
		StudentInfoDto studentInfoDto = new StudentInfoDto(
				student.getId(),
				student.getFirstName(),
				student.getLastName(),
				student.getEmail()
		);

		return studentInfoDto;
	}
	
	public static Student fromStudentInfoDto(StudentInfoDto studentInfoDto) {
		Student student = new Student();
		
//		student.setId(studentInfoDto.id());
		student.setFirstName(studentInfoDto.first_name());
		student.setLastName(studentInfoDto.last_name());
		student.setEmail(studentInfoDto.email());
		return student;
	}
}
