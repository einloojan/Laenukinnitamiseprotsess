package ee.jan.Laenukinnitamiseprotsess;

import ee.jan.Laenukinnitamiseprotsess.config.LoanProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(LoanProperties.class)
public class LaenukinnitamiseprotsessApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaenukinnitamiseprotsessApplication.class, args);
	}
}