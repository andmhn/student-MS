package student_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import student_api.controller.dto.StudentInfoDto;
import student_api.exeptions.DuplicatedInfoException;
import student_api.exeptions.NotFoundException;
import student_api.model.Student;
import student_api.repository.StudentRepository;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
	@Autowired
	StudentRepository studentRepository;

	@GetMapping("/{student_id}")
	public StudentInfoDto getCurrentStudent(@PathVariable Long student_id) {

		Student student = studentRepository.findById(student_id)
				.orElseThrow(() -> new NotFoundException(String.format("Student with id : %d not Found", student_id)));

		return DtoMapper.toStudentInfoDto(student);
	}

	@PostMapping
	public ResponseEntity<StudentInfoDto> addStudent(@RequestBody StudentInfoDto studentRequest) {
		Student student = DtoMapper.fromStudentInfoDto(studentRequest);

		if (studentRepository.existsByEmail(studentRequest.email())) {
			throw new DuplicatedInfoException(
					String.format("Student with email : %s already exists", studentRequest.email()));
		}

		StudentInfoDto savedInfo = DtoMapper.toStudentInfoDto(studentRepository.save(student));

		return ResponseEntity.ok(savedInfo);
	}

	@DeleteMapping("/{student_id}")
	public ResponseEntity<StudentInfoDto> deleteStudent(@PathVariable Long student_id) {
		if (studentRepository.existsById(student_id)) {

			StudentInfoDto student = DtoMapper.toStudentInfoDto(studentRepository.findById(student_id).get());
			studentRepository.deleteById(student_id);
			return ResponseEntity.ok(student);

		} else {
			throw new NotFoundException(String.format("Student with id : %d not Found", student_id));
		}
	}
}
