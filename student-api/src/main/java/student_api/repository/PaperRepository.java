package student_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import student_api.model.Paper;

public interface PaperRepository extends JpaRepository<Paper, String>{

}
