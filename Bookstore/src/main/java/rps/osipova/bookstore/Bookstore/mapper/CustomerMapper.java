package rps.osipova.bookstore.Bookstore.mapper;

import org.mapstruct.Mapper;
import rps.osipova.bookstore.Bookstore.dto.CustomerDTO;
import rps.osipova.bookstore.Bookstore.dto.SignUpDTO;
import rps.osipova.bookstore.Bookstore.models.Customer;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer transform(SignUpDTO signUpDTO);

    Customer transform(CustomerDTO customerDTO);

    CustomerDTO transform(Customer customer);

    List<CustomerDTO> transform(List<Customer> customers);

    List<Customer> transformFromDTO(List<CustomerDTO> customers);


}
