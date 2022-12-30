package dev.hectorgallego.springbootsecurityjwtrestmysql;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import dev.hectorgallego.springbootsecurityjwtrestmysql.model.Role;
import dev.hectorgallego.springbootsecurityjwtrestmysql.model.User;
import dev.hectorgallego.springbootsecurityjwtrestmysql.service.RoleService;
import dev.hectorgallego.springbootsecurityjwtrestmysql.service.UserService;

@SpringBootApplication
public class SpringbootSecurityJwtRestMysqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSecurityJwtRestMysqlApplication.class, args);
	}

}

/*Clase para gaurdar registros en la base de datos apenas se inicie la aplicación */
@Component
class DemoCommandLineRunner implements CommandLineRunner{

	Logger logger = LoggerFactory.getLogger(DemoCommandLineRunner.class);

	
	private UserService userService;
	private RoleService roleService;


	public DemoCommandLineRunner(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	
	}


	@Override
	public void run(String... args) throws Exception {

		Role roleAdmin = new Role();
		roleAdmin.setRole("admin");

		Role roleUser = new Role();
		roleUser.setRole("user");

		roleService.saveRole(roleUser);
		roleService.saveRole(roleAdmin);

		List<Role> rolesUser = new ArrayList<>();
		rolesUser.add(roleUser);

		
		User userUser = new User();
		userUser.setNombre("usuario");
		userUser.setUsername("user");
		userUser.setPassword("user");
		userUser.setEmail("user@gmail.com");
		userUser.setRoles(rolesUser);

		List<Role> rolesAdmin = new ArrayList<>();
		rolesAdmin.add(roleUser);
		rolesAdmin.add(roleAdmin);

		User userAdmin = new User();
		userAdmin.setNombre("administrador");
		userAdmin.setUsername("admin");
		userAdmin.setPassword("admin");
		userAdmin.setEmail("admin@gmail.com");
		userAdmin.setRoles(rolesAdmin);

		userService.saveUser(userAdmin);
		userService.saveUser(userUser);

		logger.info("role "+roleAdmin.getRole() + " creado");
		logger.info("role "+roleUser.getRole() + " creado");

		logger.info("user "+userAdmin.getUsername() + " creado");
		logger.info("user "+userUser.getUsername() + " creado");
	
	}
}


