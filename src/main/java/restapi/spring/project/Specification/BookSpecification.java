package restapi.spring.project.Specification;

import restapi.spring.project.Model.BookModel;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class BookSpecification {
    public static Specification<BookModel> withFilters(String search, String category) {

        return (root, query, cb) ->{
            List<Predicate> predicates = new ArrayList<>();
            
            if (search != null && search.isBlank()){

                String like = "%"  + search.toLowerCase() + "%";

                predicates.add(
                    cb.like(cb.lower(root.get("name")), like)
                );
            }
            if (category != null && category.isBlank()) {
                
                predicates.add(
                    cb.equal(root.get("category"), category)
                );
            }
        

            return cb.and(predicates.toArray(new Predicate[0]));
        };

    }

    public static Specification<BookModel> withCategory(String category) {
        
        // Não lembro exatamente oque o Specificantion faz, tenho que dar uma anotada dps
        return(root, query, cb) -> {
            if (category != null && category.isBlank()) {
                return cb.equal(root.get("category"), category);
            }
            return cb.conjunction(); // No filtering if category is not provided
        };
    }

    public static Specification<BookModel> withSearch(String search) {
        
        // Não lembro exatamente oque o Specificantion faz, tenho que dar uma anotada dps
        return(root, query, cb) -> {
            if (search != null && search.isBlank()) {
                String like = "%"  + search.toLowerCase() + "%";
                return cb.like(cb.lower(root.get("name")), like);
            }
            return cb.conjunction(); 
        };
     }

     public static Specification<BookModel> withAvailability(Boolean available) {
        return (root, query, cb) -> {
            if (available != null) {
                return cb.equal(root.get("available"), available);
            }
            return cb.conjunction(); 
        };
    
     }
}
