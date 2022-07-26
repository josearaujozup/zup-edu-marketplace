package br.com.zup.edu.marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
//@EnableScheduling
public class MarketplaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceApplication.class, args);
	}

}
