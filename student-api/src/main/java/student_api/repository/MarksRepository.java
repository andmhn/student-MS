package student_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import student_api.model.Marks;

public interface MarksRepository extends JpaRepository<Marks, String>{

}
