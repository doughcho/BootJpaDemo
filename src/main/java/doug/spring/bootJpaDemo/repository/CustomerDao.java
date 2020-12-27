package doug.spring.bootJpaDemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import doug.spring.bootJpaDemo.model.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {
	@Query(value="SELECT c FROM Customer c " + "WHERE c.custId = :custId")
	Optional<Customer> findById(int custId);
}