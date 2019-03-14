package br.com.pan.challenge.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LocationTest {
	
    @LocalServerPort
    private int port;
	
    @Autowired
    private TestRestTemplate restTemplate;
    
    /**
     * Teste de performance e cache da API. 
     * Obs.: Em teste local 1000 chamadas ao endpoint de estados (states) foi retornada com sucesso em 7 segundos (OK)
     */
	@Test
	public void testMassiveCallToStateEndpoint() {
    	int amountOfTimeToCall = 1000;
    	
    	LocalDateTime startDate = LocalDateTime.now();    	
    	System.out.println(startDate);
    	for(int i=0; i<amountOfTimeToCall; i++) {
    		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/location/state", String.class)).contains("id");
    	}
    	LocalDateTime endDate = LocalDateTime.now(); 
    	System.out.println(endDate);
    	System.out.println("The execution of " + amountOfTimeToCall + " calls to the states endpoint was performed, all calls were successfully executed (code and content OK). The operation has been perfomed on " + startDate.until(endDate, ChronoUnit.SECONDS) + " second(s)");   	
	}

}
