package rps.osipova.bookstore.Bookstore.mapper;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import rps.osipova.bookstore.Bookstore.dto.AuthorDTO;
import rps.osipova.bookstore.Bookstore.dto.ProductDTO;
import rps.osipova.bookstore.Bookstore.models.Author;
import rps.osipova.bookstore.Bookstore.models.Product;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product transform(ProductDTO productDTO);

    ProductDTO transform(Product product);

    List<Product> transformFromProductDTOList(List<ProductDTO> productDTO);

    List<ProductDTO> transformFromProductList(List<Product> product);

    default Page<ProductDTO> transformFromPage(Page<Product> products) {
        List<ProductDTO> productDTOS = products.getContent().stream()
                .map(this::transform)
                .collect(Collectors.toList());
        return new PageImpl<>(productDTOS, products.getPageable(), products.getTotalElements());
    }

    List<Author> fromListAuthorDTO(List<AuthorDTO> authorDTOList);
}
