package doug.spring.bootJpaDemo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doug.spring.bootJpaDemo.repository.ItemDao;
import doug.spring.bootJpaDemo.model.Item;

@Service
public class ItemService {
	@Autowired
	private ItemDao itemDao;
	
	public Optional<Item> findById(int itemId) {
		Optional<Item> item = itemDao.findById(itemId);
		return item;
	}
}
