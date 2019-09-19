package socialnetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
        //Start database server
        //OrientDBServerManager.startOrientDBServer("Test node", "test");


		SpringApplication.run(Application.class, args);
	}

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("POST", "GET",  "PUT", "OPTIONS", "DELETE")
                        .allowedHeaders("X-Auth-Token", "Content-Type")
                        .exposedHeaders("custom-header1", "custom-header2")
                        .allowCredentials(false)
                        .maxAge(4800);
            }
        };
    }
}
