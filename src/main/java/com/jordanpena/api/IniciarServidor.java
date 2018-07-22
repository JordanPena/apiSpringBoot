package com.jordanpena.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.jordanpena.api.config.ConfigStorage;

@SpringBootApplication
@EnableConfigurationProperties({
    ConfigStorage.class
})
public class IniciarServidor {

	public static void main(String[] args) {
		SpringApplication.run(IniciarServidor.class, args);
	}
}
