package doug.spring.bootJpaDemo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import doug.spring.bootJpaDemo.model.OrderItem;

@Repository
public interface OrderItemDao extends JpaRepository<OrderItem, Integer> {
	@Query("SELECT oi FROM OrderItem oi WHERE oi.pk.orderId = :orderId AND oi.pk.seq = :seq")
	Optional<OrderItem> findByPk(int orderId, int seq);
	
	@Query("SELECT oi FROM OrderItem oi WHERE oi.pk.orderId = :orderId ORDER BY oi.pk.seq ASC")
	List<OrderItem> findByOrderId(int orderId);
		
	@Transactional
	@Modifying
	@Query("DELETE FROM OrderItem oi WHERE oi.pk.orderId = :orderId AND oi.pk.seq = :seq")
	void deleteByPk(int orderId, int seq);
}