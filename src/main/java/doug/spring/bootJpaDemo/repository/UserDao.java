package doug.spring.bootJpaDemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import doug.spring.bootJpaDemo.model.User;

@Repository
@Transactional(readOnly = true)
public interface UserDao extends JpaRepository<User, String> {
//	@Query(nativeQuery=true, value="SELECT u FROM User u "
//			+ "WHERE u.userId = :userId")
	Optional<User> findById(String userId);
}
