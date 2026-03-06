package restapi.spring.project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import restapi.spring.project.Dto.request.LoginRequest;
import restapi.spring.project.Dto.request.RegisterRequest;
import restapi.spring.project.Model.BookModel;
import restapi.spring.project.Services.*;

import restapi.spring.project.Model.RentalModel;

import static restapi.spring.project.Enum.Role.ADMIN;
import static restapi.spring.project.Enum.Role.MANAGER;

import java.time.LocalDate;

@SpringBootApplication
@EnableScheduling
public class ProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner (
			AuthenticationService service,
			BookService bookService,
            RentalService rentalService
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


 // ── Books ─────────────────────────────────────────────────
 			bookService.saveBook(BookModel.builder()
 			.title("Clean Code")
 			.author("Robert C. Martin")
 			.isbn("978-0132350884")
 			.genre("Technology")
 			.publishedYear(2008)
 			.description("A handbook of agile software craftsmanship.")
 			.isAvailable(true)
 			.build());

            bookService.saveBook(BookModel.builder()
        	.title("The Pragmatic Programmer")
        	.author("Andrew Hunt")
        	.isbn("978-0201616224")
        	.genre("Technology")
        	.publishedYear(1999)
        	.description("From journeyman to master.")
        	.isAvailable(true)
        	.build());

            bookService.saveBook(BookModel.builder()
        	.title("Design Patterns")
        	.author("Gang of Four")
        	.isbn("978-0201633610")
        	.genre("Technology")
        	.publishedYear(1994)
        	.description("Elements of reusable object-oriented software.")
        	.isAvailable(false)
        	.build());


            // ── Rentals ───────────────────────────────────────────────
            rentalService.saveRental(RentalModel.builder()
        	.userId(1L)
        	.bookId(1L)
        	.rentalDate(LocalDate.now())
        	.returnDate(LocalDate.now().plusDays(14))
        	.build());

			rentalService.saveRental(RentalModel.builder()
        	.userId(2L)
        	.bookId(2L)
        	.rentalDate(LocalDate.now().minusDays(5))
        	.returnDate(LocalDate.now().plusDays(9))
        	.build());

            System.out.println("=== Seed concluído com sucesso ===");

		};
	}
}

