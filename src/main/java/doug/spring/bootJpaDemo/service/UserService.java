package doug.spring.bootJpaDemo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import doug.spring.bootJpaDemo.repository.UserDao;
import doug.spring.bootJpaDemo.model.User;

@Service
//@Configurable
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public Optional<User> findById(String userId) {
		Optional<User> user = userDao.findById(userId);
		return user;
	}
}
