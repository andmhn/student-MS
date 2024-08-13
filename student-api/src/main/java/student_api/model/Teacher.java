package student_api.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "teacher")
@Data
public class Teacher {
	@Id
	@Column(unique = true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(unique = true)
	private String email;

	private String firstName;
	private String lastName;
	private String password;

	@OneToMany
	private List<ClassInfo> classes;

	@OneToMany
	private List<PaperDetails> papers;

	@OneToMany
	private List<Student> students;
}
