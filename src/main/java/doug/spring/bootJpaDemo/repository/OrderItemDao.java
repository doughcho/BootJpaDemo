package doug.spring.bootJpaDemo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import doug.spring.bootJpaDemo.model.OrderItem;

@Repository
@Transactional
public interface OrderItemDao extends JpaRepository<OrderItem, Integer> {
	@Query("SELECT oi FROM OrderItem oi WHERE oi.pk.orderId = :orderId AND oi.pk.seq = :seq")
	Optional<OrderItem> findByPk(int orderId, int seq);
	
	@Query("SELECT oi FROM OrderItem oi WHERE oi.pk.orderId = :orderId")
	List<OrderItem> findByOrderId(int orderId);
		
	@Query("DELETE FROM OrderItem oi WHERE oi.pk.orderId = :orderId AND oi.pk.seq = :seq")
	void deleteByPk(int orderId, int seq);
}