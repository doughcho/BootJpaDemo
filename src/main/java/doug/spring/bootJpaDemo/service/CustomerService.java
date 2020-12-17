package doug.spring.bootJpaDemo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doug.spring.bootJpaDemo.repository.CustomerDao;
import doug.spring.bootJpaDemo.model.Customer;

@Service
public class CustomerService {
	@Autowired
	private CustomerDao customerDao;
	
	public Optional<Customer> findById(int custId) {
		Optional<Customer> customer = customerDao.findById(custId);
		return customer;
	}
}
