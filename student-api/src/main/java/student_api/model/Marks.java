package student_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Marks {
	@Id
	@Column(unique = true)
	private String student_email;
	private String paperNo;
	private Float marks;
}
