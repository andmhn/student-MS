package student_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import student_api.model.Result;

public interface ResultRepository extends JpaRepository<Result, Long>{
	@Query("SELECT r FROM Result r WHERE r.student_email = :email")
	List<Result> findByEmail(@Param("email") String email);
}
