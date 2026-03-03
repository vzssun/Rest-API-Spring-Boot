package restapi.spring.project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class BookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private ReservationModel reservation; // entidade, não Long

    private String reserverName;
    private String title;
    private String author;
    private String isbn;
    private String genre;
    private String coverImageUrl;
    private int publishedYear;
    private boolean isAvailable;
    private String description;
    private String reservationPeriod;


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



  /*  public BookModel(String title, String author, String isbn, String genre, String coverImageUrl, int publishedYear, String description) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.coverImageUrl = coverImageUrl;
        this.publishedYear = publishedYear;
        this.description = description;
    }
}  */
}