package rps.osipova.bookstore.Bookstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rps.osipova.bookstore.Bookstore.dto.CustomerDTO;
import rps.osipova.bookstore.Bookstore.dto.ProductDTO;
import rps.osipova.bookstore.Bookstore.dto.ReviewDTO;
import rps.osipova.bookstore.Bookstore.mapper.ProductMapper;
import rps.osipova.bookstore.Bookstore.models.Product;
import rps.osipova.bookstore.Bookstore.repository.ProductRepository;
import rps.osipova.bookstore.Bookstore.service.CustomerService;
import rps.osipova.bookstore.Bookstore.service.GeneralService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/general")
public class GeneralController {

    private final GeneralService generalService;
    private final ProductMapper productMapper;
    private final CustomerService customerService;
    private final ProductRepository productRepository;

    @Autowired
    public GeneralController(GeneralService generalService, ProductMapper productMapper, CustomerService customerService, ProductRepository productRepository) {
        this.generalService = generalService;
        this.productMapper = productMapper;
        this.customerService = customerService;
        this.productRepository = productRepository;
    }

    /**
     * Получение всех товаров
     */
    @GetMapping("/allProducts")
    public List<ProductDTO> getAllProducts() {
        return generalService.getAllProducts();
    }

    /**
     * Получение всех товаров с пагинацией на странице
     */
    @GetMapping("/products")
    public Page<ProductDTO> getProductsWithPagination(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size,
                                                      @RequestParam(required = false) String fieldForSort) {
        if (fieldForSort != null) {
            return generalService.getAllProducts(page, size, fieldForSort);
        } else {
            return generalService.getAllProducts(page, size, "name");
        }
    }

    /**
     * Получение продукта по id
     */
    @GetMapping("product")
    public ResponseEntity<?> getProduct(@RequestParam String productId) {
        if (productId != null) {
            Product product = generalService.getProductById(productId);
            if (product != null) {
                ProductDTO productDTO = productMapper.transform(product);
                return new ResponseEntity<>(productDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Не удалось получить запись товара.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Не удалось получить запись товара.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("productByName")
    public Page<ProductDTO> getProductByName(@PageableDefault(page = 0, size = 10) Pageable pageable,
                                             @RequestParam String name) {
        Product searchPattern = new Product();
        if (name != null && !name.isEmpty()) {
            searchPattern.setName(name);
        }
        Page<Product> resultPage = productRepository.findAll(searchPattern, pageable);
        Page<ProductDTO> response = productMapper.transformFromPage(resultPage);
        return response;
    }

    /**
     * Метод для получения списка всех пользователей - покупателей (Customer)
     * Для отображения на фронте списка покупателей, чтобы была возможность добавлять их в ДРУЗЬЯ
     */
    @GetMapping("/allCustomers")
    public ResponseEntity<?> getAllCustomers(@RequestParam(required = false) String myId) {
        if (myId != null) {
            List<CustomerDTO> customerDTOList = customerService.findAllCustomersWithOutMe(myId);
            return new ResponseEntity<>(customerDTOList, HttpStatus.OK);
        } else {
            List<CustomerDTO> customerDTOList = customerService.findAllCustomers();
            return new ResponseEntity<>(customerDTOList, HttpStatus.OK);
        }
    }

    /**
     * Метод для получения отзывов для всех пользователей по id продукта
     */
    @GetMapping("/reviewForAllByProductId")
    public ResponseEntity<?> getReviewForAllByProductId(@RequestParam String productId) {
        if (productId != null) {
            List<ReviewDTO> response = generalService.getReviewForProductForAllUsers(productId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Невозможно получить отзывы о товаре.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getCustomer")
    public ResponseEntity<?> getCustomerById(@RequestParam String customerId) {
        if (customerId != null) {
            CustomerDTO customerDTO = generalService.getCustomerById(customerId);
            if (customerDTO != null) {
                return new ResponseEntity<>(customerDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Невозможно получить данные о пользователе.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Невозможно получить данные о пользователе.", HttpStatus.BAD_REQUEST);
        }
    }
}
