package doug.spring.bootJpaDemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import doug.spring.bootJpaDemo.model.User;

@Repository
public interface UserDao extends JpaRepository<User, String> {
	@Query(value="SELECT u FROM User u " + "WHERE u.userId = :userId")
	Optional<User> findById(String userId);
}
