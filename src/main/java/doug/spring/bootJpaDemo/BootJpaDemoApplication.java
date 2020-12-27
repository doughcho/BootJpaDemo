package doug.spring.bootJpaDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories("doug.spring.bootJpaDemo.repository")  // Bootstrap mode: DEFERRED -> DEFAULT
public class BootJpaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootJpaDemoApplication.class, args);
	}

}
