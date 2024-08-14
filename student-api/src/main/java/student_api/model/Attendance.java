package student_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "student_attendance")
public class Attendance {
	@Id
	@Column(unique = true)
	private String student_email;

	private String classId;
}
