package doug.spring.bootJpaDemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import doug.spring.bootJpaDemo.model.Item;

@Repository
@Transactional(readOnly = true)
public interface ItemDao extends JpaRepository<Item, Integer> {
	Optional<Item> findById(int itemId);
}