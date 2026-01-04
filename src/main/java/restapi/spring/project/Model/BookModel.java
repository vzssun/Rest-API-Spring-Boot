package restapi.spring.project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
// if using Lombok
@Getter
@Setter
@Table(name = "books")
public class BookModel {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id; // primary key for the book
    private String title;
    private String author;
    private String isbn;
    private String genre;
    private String coverImageUrl; // URL of the cover image
    private int publishedYear;
    private String description;

    // Not needed with Lombok
    
    // public void setTitle(String title) {
    //     this.title = title;
    // }

    // public String getTitle() {
    //     return title;
    // }

    // public void setAuthor(String author) {
    //     this.author = author;
    // }

    // public String getAuthor() {
    //     return author;
    // }

    public BookModel() {
        // Default constructor for JPA
    }

    public BookModel(String title, String author, String isbn, String genre, String coverImageUrl, int publishedYear, String description) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.coverImageUrl = coverImageUrl;
        this.publishedYear = publishedYear;
        this.description = description;
    }
}