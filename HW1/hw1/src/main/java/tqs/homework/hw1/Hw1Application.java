package tqs.homework.hw1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;


@SpringBootApplication
@Component
@EnableScheduling
@EnableCaching
public class Hw1Application {

	
	
		
	public static void main(String[] args) {
		SpringApplication.run(Hw1Application.class, args);
	}

}
