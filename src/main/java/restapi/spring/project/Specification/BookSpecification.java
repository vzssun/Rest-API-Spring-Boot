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
    
}
