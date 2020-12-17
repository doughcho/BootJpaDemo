package doug.spring.bootJpaDemo.model;

import java.io.Serializable;
import java.sql.Date;
//import java.util.List;
import javax.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "order2")
@Getter @Setter
public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @Column(name = "ORDER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	@Column(name = "CUST_ID")	
	private int custId;
	@Column(name = "EMPL_ID")	
	private int emplId;
	@Column(name = "ODATE")
	private Date orderDt;
	@Column(name = "COMMT")
	private String comment;
	@Column(name = "OSTAT")
	private String status;
	@Column(name = "SMTHD")
	private String shipMthd;
	@Column(name = "SADDR")
	private String shipAddr;
	@Column(name = "OPAYR")
	private String shipPayr;
	@Column(name = "TRACK")
	private String trackNum;
	@Column(name = "CRNM")
	private String crName;		
	@Column(name = "CRDT")
	private Date crDt;
	@Column(name = "UPDNM")
	private String updName;
	@Column(name = "UPDDT")
	private Date updDt;
	@Column(name = "SHCHG")
    private float   shipChrg;
	@Column(name = "DCAMT")
	private float   dcAmt;
	@Column(name = "PAMT")
	private float   paidAmt;
	@Column(name = "TXEMT")
	private boolean taxExmt;
	public Order() {
		super();
	}
//	@ManyToOne
//	@JoinColumn(name = "CUST_ID", insertable = false, updatable = false)
//	private Customer customer;	// for custName
//	@ManyToOne
//	@JoinColumn(name = "EMPL_ID", insertable = false, updatable = false)
//	private Employee employee;	// for emplName
//	@OneToMany
//	@JoinColumn(name = "ORDER_ID")
//	private List<OrderItem> orderItems;
}
