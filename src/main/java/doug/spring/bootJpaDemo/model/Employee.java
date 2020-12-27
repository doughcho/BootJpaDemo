package doug.spring.bootJpaDemo.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "employees")
@Getter @Setter
public class Employee implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "EMPL_ID")
	private int emplId;
	@Column(name = "NAME")
	private String emplName;
	@Column(name = "DEPT")
	private int emplDept;
	@Column(name = "MGR")
	private int emplMgr;
	@Column(name = "TEL", nullable = true)
	private String emplTel;
	@Column(name = "EMAIL", nullable = true)
	private String emplEmail;
	@Column(name = "ACTIV")	
	private boolean active;
}
