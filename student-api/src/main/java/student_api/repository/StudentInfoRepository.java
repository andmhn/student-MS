package student_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import student_api.model.StudentInfo;

public interface StudentInfoRepository extends JpaRepository<StudentInfo, String> {

}
