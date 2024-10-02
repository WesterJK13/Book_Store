package rps.osipova.bookstore.Bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rps.osipova.bookstore.Bookstore.dto.CustomerDTO;
import rps.osipova.bookstore.Bookstore.dto.ProductDTO;
import rps.osipova.bookstore.Bookstore.dto.ReviewDTO;
import rps.osipova.bookstore.Bookstore.mapper.CustomerMapper;
import rps.osipova.bookstore.Bookstore.mapper.ProductMapper;
import rps.osipova.bookstore.Bookstore.mapper.ReviewMapper;
import rps.osipova.bookstore.Bookstore.models.Customer;
import rps.osipova.bookstore.Bookstore.models.Product;
import rps.osipova.bookstore.Bookstore.models.Review;
import rps.osipova.bookstore.Bookstore.repository.CustomerMainRepository;
import rps.osipova.bookstore.Bookstore.repository.ProductRepository;
import rps.osipova.bookstore.Bookstore.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GeneralService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final CustomerMainRepository customerMainRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public GeneralService(ProductRepository productRepository, ProductMapper productMapper, ReviewRepository reviewRepository, ReviewMapper reviewMapper, CustomerMainRepository customerMainRepository, CustomerMapper customerMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.customerMainRepository = customerMainRepository;
        this.customerMapper = customerMapper;
    }

    /**
     * Получение всех продуктов (книг) в магазине
     */
    public List<ProductDTO> getAllProducts() {
//        return productMapper.transformFromProductList(productRepository.findAll());
        return productMapper.transformFromProductList(productRepository.findAll(Sort.by("name")));
    }

    public Page<ProductDTO> getAllProducts(int page, int size, String fieldForSort) {
        Page<Product> products;
        if (!Objects.equals(fieldForSort, "availability")) {
            products = productRepository.findAll(PageRequest.of(page, size, Sort.by(fieldForSort)));
        } else {
            products = productRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, fieldForSort)));
        }
        return productMapper.transformFromPage(products);
    }

    public Product getProductById(String productId) {
        return productRepository.findById(productId).orElse(null);
    }


    public List<ReviewDTO> getReviewForProductForAllUsers(String productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            List<Review> reviews = reviewRepository.findAllByProduct(product);
            List<ReviewDTO> reviewDTOS = new ArrayList<>();
            for (Review review: reviews) {
                if (review.getCommentForAll() != null) {
                    reviewDTOS.add(reviewMapper.transform(review));
                }
            }
            return reviewDTOS;
        } else {
            return null;
        }
    }

    public CustomerDTO getCustomerById(String customerId) {
        Customer customer = customerMainRepository.findById(customerId).orElse(null);
        return customerMapper.transform(customer);
    }
}
