package com.finance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);



		//temp
		// TEMPORARY - remove after use
        //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //String hash = encoder.encode("password123");
        //System.out.println("=== FRESH HASH: " + hash + " ===");
	}

}
