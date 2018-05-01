package pl.saz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "pl")
public class SazwarehouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SazwarehouseApplication.class, args);
	}
}
