package student_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import student_api.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
	@Query("SELECT a FROM Attendance a WHERE a.student_email = :email")
	List<Attendance> findByEmail(@Param("email") String email);
}
