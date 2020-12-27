package doug.spring.bootJpaDemo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import doug.spring.bootJpaDemo.model.Order;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {
	@Query("SELECT o FROM Order o WHERE o.orderId = :orderId")
	Optional<Order> findById(int orderId);

	@Query("SELECT o FROM Order o WHERE o.custId = :custId ORDER BY o.orderId DESC")
	List<Order> findByCustId(int custId);

	@Transactional  
	@Modifying
	@Query("UPDATE Order o SET o.status = '9' WHERE o.orderId = :orderId")
	void cancelOrder(int orderId);
}