package student_api.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "student_info")
public class StudentInfo {
	@Id
	@Column(unique = true)
	private String email;
	
	@OneToMany
	private List<Attendance> attendances;
	
	@OneToMany
	private List<Result> results;
}
