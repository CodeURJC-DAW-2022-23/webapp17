package es.codeurjc.webapp17;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// (exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
public class Webapp17Application {

	public static void main(String[] args) {
		SpringApplication.run(Webapp17Application.class, args);
	}

}
