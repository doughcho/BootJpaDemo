package doug.spring.bootJpaDemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doug.spring.bootJpaDemo.repository.OrderItemDao;
import doug.spring.bootJpaDemo.model.OrderItem;

@Service
public class OrderItemService {
	@Autowired
	private OrderItemDao orderItemDao;
	
	public Optional<OrderItem> findByPk(int orderId, int seq) {
		Optional<OrderItem> orderItem = orderItemDao.findByPk(orderId, seq);
		return orderItem;
	}
	
	public List<OrderItem> findByOrderId(int orderId) {
		List<OrderItem> orderItems = new ArrayList<>();
		orderItemDao.findByOrderId(orderId).forEach(e -> orderItems.add(e));
		return orderItems;
	}
		
	public OrderItem save(OrderItem orderItem) {
		orderItemDao.save(orderItem);
		return orderItem;
	}
	
	public void updateByPk(int orderId, int seq, OrderItem orderItem) {
		Optional<OrderItem> oi = orderItemDao.findByPk(orderId, seq);
		if (oi.isPresent()) {
			oi.get().setItemId(orderItem.getItemId());
			oi.get().setQty(orderItem.getQty());
			oi.get().setUPrice(orderItem.getUPrice());
			oi.get().setComment(orderItem.getComment());
			oi.get().setUpdDt(orderItem.getUpdDt());
			orderItemDao.save(orderItem);
		}
	}
	
	public void deleteByPk(int orderId, int seq) {
		orderItemDao.deleteByPk(orderId, seq);
	}
}