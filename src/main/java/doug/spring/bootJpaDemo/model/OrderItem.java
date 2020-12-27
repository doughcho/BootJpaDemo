package doug.spring.bootJpaDemo.model;

import java.io.Serializable;
import lombok.*;
import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "orderitem2")
@Getter @Setter
public class OrderItem implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OrderItemPK pk;
	@Column(name = "ITEM_ID")	
	private int itemId;
	@Column(name = "QTY")	
	private int qty;
	@Column(name = "PRICE")	
	private float uPrice;
	@Column(name = "COMMT")
	private String comment;
	@Column(name = "UPDDT")
	private String updDt;
//	@ManyToOne
//	@JoinColumn(name = "ITEM_ID", insertable = false, updatable = false)
//	private Item item;	// for itemName

	public OrderItem() {
		super();
	}
	
	public OrderItemPK getPk() {
		return pk;
	}
	
	public void setPk(OrderItemPK pk) {
		this.pk = pk;
	}
	
	public int getOrderId() {
		return this.pk.getOrderId();
	}
	
	public int getSeq() {
		return this.pk.getSeq();
	}
	
	public void setOrderId(int orderId) {
		this.pk.setOrderId(orderId);
	}

	public void setSeq(int seq) {
		this.pk.setSeq(seq);
	}
}