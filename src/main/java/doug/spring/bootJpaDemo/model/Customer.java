package doug.spring.bootJpaDemo.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "customers")
@Getter @Setter
public class Customer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "CUST_ID")
	private int custId;
	@Column(name = "NAME")
	private String name;
	@Column(name = "EMPL_ID")
	private int emplId;
	@Column(name = "ADDR")
	private String addr;
	@Column(name = "SMTHD")
	private String shipMthd;
	@Column(name = "TXEMT")
	private String taxExmt;
	@Column(name = "ACTIV")	
	private boolean active;
	public Customer() {
		super();
	}
//	@ManyToOne
//	@JoinColumn(name = "EMPL_ID", insertable = false, updatable = false)
//	private Employee employee;	// for emplName
}
