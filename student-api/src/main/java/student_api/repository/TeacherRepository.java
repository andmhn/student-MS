package student_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import student_api.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long>{

}
