package doug.spring.bootJpaDemo.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderItemPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name="ORDER_ID", nullable=false)
	private int orderId;
	
	@Column(name="SEQ", nullable=false)
	private int seq;
	
	public OrderItemPK() {}
	
	public OrderItemPK(int id, int seq) {
		super();
		this.orderId = id;
		this.seq = seq;
	}
	
	public int getOrderId() {
		return orderId;
	}
	
	public int getSeq() {
		return seq;
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof OrderItemPK)) return false;
		OrderItemPK that = (OrderItemPK) obj;
		return Objects.equals(getOrderId(), that.getOrderId()) &&
			   Objects.equals(getSeq(), that.getSeq());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getOrderId(), getSeq());
	}
}