package com.whqtker.polling_sse_ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class PollingSseWsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PollingSseWsApplication.class, args);
	}

}
