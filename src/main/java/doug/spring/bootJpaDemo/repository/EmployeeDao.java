package doug.spring.bootJpaDemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import doug.spring.bootJpaDemo.model.Employee;

@Repository
@Transactional(readOnly = true)
public interface EmployeeDao extends JpaRepository<Employee, Integer> {
	Optional<Employee> findById(int emplId);
}