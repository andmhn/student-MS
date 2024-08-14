package student_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import student_api.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, String>{

}
