package student_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "class_info")
public class ClassInfo {
	@Id
	@Column(unique = true)
	private Long id;
	private String type;
	private String date;
	private String fromTime;
	private String toTime;
}
