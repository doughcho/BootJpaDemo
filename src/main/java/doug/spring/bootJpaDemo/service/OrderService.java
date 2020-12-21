package doug.spring.bootJpaDemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import doug.spring.bootJpaDemo.repository.OrderDao;
import doug.spring.bootJpaDemo.model.Order;
import doug.spring.bootJpaDemo.model.IdAndDate;

@Service
public class OrderService {
	@Autowired
	private OrderDao orderDao;
	
	public Optional<Order> findById(int orderId) {
		Optional<Order> order = orderDao.findById(orderId);
		return order;
	}
	
	public List<IdAndDate> findByCustId(int custId) {
		List<IdAndDate> idAndDates = new ArrayList<>();
//		orderDao.findByCustId(custId).forEach(e -> idAndDates.add(e));
		List<Order> o = orderDao.findByCustId(custId);
		IdAndDate custOrder = new IdAndDate();		
		int count = o.size();
		for (int i = 0; i < count; i++) {
			custOrder.orderId = o.get(i).getOrderId();
			custOrder.orderDt = o.get(i).getOrderDt();
			idAndDates.add(custOrder);
		}
		return idAndDates;
	}
	
	public Order save(Order order) {
		orderDao.save(order);
		return order;
	}
	
	public void updateById(int orderId, Order order) {
		Optional<Order> o = orderDao.findById(orderId);
		if (o.isPresent()) {
			o.get().setCustId(order.getCustId());
			o.get().setEmplId(order.getEmplId());
			o.get().setOrderDt(order.getOrderDt());
			o.get().setComment(order.getComment());
			o.get().setStatus(order.getStatus());
			o.get().setShipMthd(order.getShipMthd());
			o.get().setShipAddr(order.getShipAddr());
			o.get().setShipPayr(order.getShipPayr());
			o.get().setTrackNum(order.getTrackNum());
			o.get().setUpdName(order.getUpdName());
			o.get().setUpdDt(order.getUpdDt());			
			o.get().setShipChrg(order.getShipChrg());
			o.get().setDcAmt(order.getDcAmt());
			o.get().setPaidAmt(order.getPaidAmt());
			o.get().setTaxExmt(order.isTaxExmt());
			orderDao.save(order);
		}
	}
	
	public void cancelOrder(int orderId) {
		orderDao.cancelOrder(orderId);
	}
}