// En un archivo separado: LiteraturaApplication.java
package com.aluraoracle.screenbook;

import com.aluraoracle.screenbook.principal.Principal;
import com.aluraoracle.screenbook.repository.AutoresRepository;
import com.aluraoracle.screenbook.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
class LiteraturaApplication implements CommandLineRunner {

	@Autowired
	private AutoresRepository autoresRespositorio;

	@Autowired
	private LibroRepository libroRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(autoresRespositorio, libroRepositorio);
		principal.mostrarElMenu();
	}
}
