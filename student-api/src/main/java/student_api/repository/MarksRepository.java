package student_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import student_api.model.Student;

public interface MarksRepository extends JpaRepository<Student, Long>{

}
