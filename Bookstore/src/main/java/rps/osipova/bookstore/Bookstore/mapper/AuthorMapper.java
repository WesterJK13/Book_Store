package rps.osipova.bookstore.Bookstore.mapper;

import org.mapstruct.Mapper;
import rps.osipova.bookstore.Bookstore.dto.AuthorDTO;
import rps.osipova.bookstore.Bookstore.models.Author;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDTO transform(Author author);
    Author transform(AuthorDTO authorDTO);

    List<Author> transformFromDTOList(List<AuthorDTO> authorDTOS);
    List<AuthorDTO> transformFromList(List<Author> authors);

}
