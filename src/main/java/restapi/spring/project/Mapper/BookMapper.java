package restapi.spring.project.Mapper;

import org.mapstruct.Mapper;
import restapi.spring.project.Dto.BookDTO;
import restapi.spring.project.Model.BookModel;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO toDTO(BookModel book); // só isso — ele gera o resto
}