package com.saransh.SpringRest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringRestApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(SpringRestApplication.class, args);
	}

	/**
	 * Callback used to run the bean.
	 *
	 * @param args incoming main method arguments
	 * @throws Exception on error
	 */
	@Override
	public void run(String... args) throws Exception {
		String line = "ttttt|N|wooho|hehe|abc.def.com||";
		String[] split = line.split("\\|", -1);
		for(String x: split)
			System.out.println(x);

	}
}
