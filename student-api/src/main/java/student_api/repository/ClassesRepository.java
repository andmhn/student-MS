package student_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import student_api.model.Classes;

public interface ClassesRepository extends JpaRepository<Classes, Long>{

}
