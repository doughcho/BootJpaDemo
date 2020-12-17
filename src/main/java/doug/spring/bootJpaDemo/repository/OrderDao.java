package doug.spring.bootJpaDemo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import doug.spring.bootJpaDemo.model.Order;
import doug.spring.bootJpaDemo.model.IdAndDate;

@Repository
@Transactional
public interface OrderDao extends JpaRepository<Order, Integer> {
	//	@Query("SELECT o FROM Order o WHERE o.orderId = :orderId")
	Optional<Order> findById(int orderId);
	@Query("SELECT orderId, OrderDt FROM Order o WHERE o.custId = :custId")
	List<IdAndDate> findByCustId(int custId);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE Order o SET o.status = '9' WHERE o.orderId = :orderId")
	void cancelOrder(int orderId);
}