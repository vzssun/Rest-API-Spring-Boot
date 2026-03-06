package restapi.spring.project.Dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long bookId;
    private String title;
    private String author;
    private String isbn;
    private String genre;
    private String category;
    private String coverImageUrl;
    private int publishedYear;
    private boolean isAvailable;
    private String description;
    private String reserverName;
    private String reservationPeriod;
    private Long reservationId;
}