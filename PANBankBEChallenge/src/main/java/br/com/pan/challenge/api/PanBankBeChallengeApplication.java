package br.com.pan.challenge.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

import br.com.pan.challenge.api.config.PanApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(PanApiProperty.class)
@EnableCaching
public class PanBankBeChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PanBankBeChallengeApplication.class, args);
	}

}
