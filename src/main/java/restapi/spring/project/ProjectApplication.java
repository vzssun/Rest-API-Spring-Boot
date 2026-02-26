package restapi.spring.project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import restapi.spring.project.Dto.request.LoginRequest;
import restapi.spring.project.Dto.request.RegisterRequest;
import restapi.spring.project.Services.AuthenticationService;

import static restapi.spring.project.Enum.Role.ADMIN;
import static restapi.spring.project.Enum.Role.MANAGER;

@SpringBootApplication
@EnableScheduling
public class ProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner (
			AuthenticationService service
	){
		return args -> {
			var adminRegister = RegisterRequest.builder()
					.fullName("Admin da silva")
					.email("admin@mail.com")
					.password("password")
					.role(ADMIN)
					.build();
			service.createUser(adminRegister);
			var adminLogin = LoginRequest.builder()
					.email("admin@mail.com")
					.password("password")
					.build();
			System.out.println("Admin Token: " + service.authenticate(adminLogin));

			var managerRegister = RegisterRequest.builder()
					.fullName("Manager da silva")
					.email("manager@mail.com")
					.password("password")
					.role(MANAGER)
					.build();
			service.createUser(managerRegister);
			var managerLogin = LoginRequest.builder()
					.email("manager@mail.com")
					.password("password")
					.build();
			System.out.println("Manager Token: " + service.authenticate(managerLogin));

		};
	}
}

