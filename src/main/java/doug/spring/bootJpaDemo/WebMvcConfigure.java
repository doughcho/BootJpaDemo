package doug.spring.bootJpaDemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@ComponentScan(basePackages = "doug.spring.bootJpaDemo")
public class WebMvcConfigure implements WebMvcConfigurer {

  @Bean		// the function is needed for using @Value annotation
  public static PropertySourcesPlaceholderConfigurer propertySourcePlaceholderConfigurer() {
	  return new PropertySourcesPlaceholderConfigurer();
  }
  
  @Override		// Resource location to /resources/static
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/**")
        .addResourceLocations("classpath:/static/").setCachePeriod(3600)
        .resourceChain(true).addResolver(new PathResourceResolver());
  }
  
  @Override		// add index page (solved Whitelabel Error Page)
  public void addViewControllers(ViewControllerRegistry registry) {
	  registry.addViewController("/").setViewName("forward:/index.html");
  }
}