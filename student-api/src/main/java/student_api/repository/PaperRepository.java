package student_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import student_api.model.Paper;

public interface PaperRepository extends JpaRepository<Paper, Long>{

	Optional<Paper> findByName(String name);
}
