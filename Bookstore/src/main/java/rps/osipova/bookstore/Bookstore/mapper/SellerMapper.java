package rps.osipova.bookstore.Bookstore.mapper;

import org.mapstruct.Mapper;
import rps.osipova.bookstore.Bookstore.dto.SellerDTO;
import rps.osipova.bookstore.Bookstore.dto.UserDTO;
import rps.osipova.bookstore.Bookstore.models.Seller;
import rps.osipova.bookstore.Bookstore.models.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    SellerDTO transform(Seller seller);

    Seller transform(SellerDTO sellerDTO);

    User transformFromSellerDTOIntoUser(SellerDTO sellerDTO);

    User transformFromUserDTOIntoUser(UserDTO sellerDTO);

    List<SellerDTO> transformList(List<Seller> sellers);

    List<Seller> transformListDTO(List<SellerDTO> sellerDTOs);

    List<SellerDTO> transformListUser(List<User> sellers);

    List<Seller> transformListUserDTO(List<UserDTO> sellerDTOs);

    SellerDTO transform(User seller);

    Seller transformFromUser(User seller);

    Seller transform(UserDTO sellerDTO);
}
