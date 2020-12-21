package doug.spring.bootJpaDemo;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	public ServletInitializer() {  // Solve ErrorPageFilter error??
		super();
		setRegisterErrorPageFilter(false);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BootJpaDemoApplication.class);
	}

}
