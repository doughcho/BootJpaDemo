package doug.spring.bootJpaDemo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;

import doug.spring.bootJpaDemo.repository.UserDao;
import doug.spring.bootJpaDemo.model.User;

@Service
//public class UserService implements UserDao {
public class UserService {
	@Autowired
	private UserDao userDao;
	
//	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
//	@Override
	public Optional<User> findById(String userId) {
	    System.out.println("UserDao is " + userDao.getClass().getName());	
		Optional<User> user = userDao.findById(userId);
		return user;
	}
}
