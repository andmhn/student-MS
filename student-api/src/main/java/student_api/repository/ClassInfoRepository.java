package student_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import student_api.model.ClassInfo;

public interface ClassInfoRepository extends JpaRepository<ClassInfo, Long>{

}
