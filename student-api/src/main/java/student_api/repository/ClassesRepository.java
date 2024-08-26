package student_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import student_api.model.Classes;

public interface ClassesRepository extends JpaRepository<Classes, Long>{
    List<Classes> findAllByOrderByName();
    List<Classes> findByNameContainingIgnoreCaseOrderByName(String name);
}
