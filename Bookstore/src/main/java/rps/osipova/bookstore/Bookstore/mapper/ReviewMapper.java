package rps.osipova.bookstore.Bookstore.mapper;

import org.mapstruct.Mapper;
import rps.osipova.bookstore.Bookstore.dto.ReviewDTO;
import rps.osipova.bookstore.Bookstore.models.Review;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review transform(ReviewDTO reviewDTO);

    ReviewDTO transform(Review review);

    List<ReviewDTO> transform(List<Review> reviews);

}
