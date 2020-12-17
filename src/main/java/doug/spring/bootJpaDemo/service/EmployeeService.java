package doug.spring.bootJpaDemo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doug.spring.bootJpaDemo.repository.EmployeeDao;
import doug.spring.bootJpaDemo.model.Employee;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeDao employeeDao;
	
	public Optional<Employee> findById(int emplId) {
		Optional<Employee> employee = employeeDao.findById(emplId);
		return employee;
	}
}
