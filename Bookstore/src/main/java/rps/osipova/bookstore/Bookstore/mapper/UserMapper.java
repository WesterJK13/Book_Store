package rps.osipova.bookstore.Bookstore.mapper;

import org.mapstruct.Mapper;
import rps.osipova.bookstore.Bookstore.dto.UserDTO;
import rps.osipova.bookstore.Bookstore.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User transform(UserDTO userDTO);
    UserDTO transform(User user);

}
