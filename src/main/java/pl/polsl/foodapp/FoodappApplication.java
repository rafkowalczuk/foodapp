package pl.polsl.foodapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FoodappApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodappApplication.class, args);
	}

}
