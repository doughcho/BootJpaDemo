package doug.spring.bootJpaDemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import doug.spring.bootJpaDemo.model.Item;

@Repository
public interface ItemDao extends JpaRepository<Item, Integer> {
	@Query(value="SELECT i FROM Item i " + "WHERE i.itemId = :itemId")
	Optional<Item> findById(int itemId);
}