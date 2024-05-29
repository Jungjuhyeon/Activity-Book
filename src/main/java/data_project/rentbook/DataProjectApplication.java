package data_project.rentbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class DataProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataProjectApplication.class, args);
	}

}
