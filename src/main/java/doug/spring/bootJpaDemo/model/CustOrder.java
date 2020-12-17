package doug.spring.bootJpaDemo.model;

public class CustOrder {
	public int custId;
	public int ordCnt;
	public IdAndDate[] cOrds;
	public CustOrder(int Id, int count) {
		if (count > 0) {
			this.cOrds = new IdAndDate[count];	// reference to objects
			for (int i = 0; i < count; i++) this.cOrds[i] = new IdAndDate();
		}
		this.custId = Id;
		this.ordCnt = count;
	}
}
