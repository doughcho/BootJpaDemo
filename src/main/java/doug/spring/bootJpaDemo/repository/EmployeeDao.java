package doug.spring.bootJpaDemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import doug.spring.bootJpaDemo.model.Employee;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer> {
	@Query(value="SELECT e FROM Employee e " + "WHERE e.emplId = :emplId")
	Optional<Employee> findById(int emplId);
}