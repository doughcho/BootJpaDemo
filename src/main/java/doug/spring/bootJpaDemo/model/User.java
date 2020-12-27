package doug.spring.bootJpaDemo.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "users")
@Getter @Setter
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USER_ID")
	private String userId;
	@Column(name = "PASSWORD")
	private String password;
	@Column(name = "ACTIVE")
	private boolean active;
	@Column(name = "NAME")
	private String name;
	@Column(name = "EMPL_ID")
	private int emplId;
	
	public User() {
		super();
	}
}
