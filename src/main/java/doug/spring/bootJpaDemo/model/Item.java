package doug.spring.bootJpaDemo.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "items")
@Getter @Setter
public class Item implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ITEM_ID")
	private int itemId;
	@Column(name = "NAME")
	private String name;
	@Column(name = "COST")
	private float cost;
	@Column(name = "PRICE")
	private float price;
	@Column(name = "ACTIV")	
	private boolean active;
}