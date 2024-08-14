package student_api.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Paper {
	@Id
	private String id;
	private Date date;
	private String type;
}
