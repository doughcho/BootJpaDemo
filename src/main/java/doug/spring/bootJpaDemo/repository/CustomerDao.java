package doug.spring.bootJpaDemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import doug.spring.bootJpaDemo.model.Customer;

@Repository
@Transactional(readOnly = true)
public interface CustomerDao extends JpaRepository<Customer, Integer> {
//	Optional<Customer> findByCustId(int custId);
	Optional<Customer> findById(int custId);
}