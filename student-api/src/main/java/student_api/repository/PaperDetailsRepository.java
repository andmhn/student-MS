package student_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import student_api.model.PaperDetails;

public interface PaperDetailsRepository extends JpaRepository<PaperDetails, String>{

}
