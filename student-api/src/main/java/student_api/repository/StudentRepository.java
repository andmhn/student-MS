package student_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import student_api.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
	Boolean existsByEmail(String email);
}
