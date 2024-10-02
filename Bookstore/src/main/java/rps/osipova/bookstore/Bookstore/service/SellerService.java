package rps.osipova.bookstore.Bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rps.osipova.bookstore.Bookstore.dto.*;
import rps.osipova.bookstore.Bookstore.mapper.AuthorMapper;
import rps.osipova.bookstore.Bookstore.mapper.CustomerMapper;
import rps.osipova.bookstore.Bookstore.mapper.OrderMapper;
import rps.osipova.bookstore.Bookstore.mapper.ProductMapper;
import rps.osipova.bookstore.Bookstore.models.*;
import rps.osipova.bookstore.Bookstore.repository.*;
import rps.osipova.bookstore.Bookstore.repository.RegistrAndAuth.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SellerService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final CustomerMainRepository customerMainRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final SellerMainRepository sellerMainRepository;
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public SellerService(ProductMapper productMapper, ProductRepository productRepository, CustomerRepository customerRepository, CustomerMainRepository customerMainRepository, CustomerMapper customerMapper, PasswordEncoder passwordEncoder, CustomerService customerService, OrderRepository orderRepository, OrderMapper orderMapper, SellerMainRepository sellerMainRepository, AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.customerMainRepository = customerMainRepository;
        this.customerMapper = customerMapper;
        this.passwordEncoder = passwordEncoder;
        this.customerService = customerService;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.sellerMainRepository = sellerMainRepository;
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.transform(productDTO);
        if (product.getQuantity() > 0) {
            product.setAvailability(true);
        } else {
            product.setAvailability(false);
        }
        return productMapper.transform(productRepository.save(product));
    }

    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }


    public Product editProduct(String productId, ProductDTO productDTO) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            product.setName(productDTO.getName());
            product.setDefinition(productDTO.getDefinition());
            product.setPrice(productDTO.getPrice());
            product.setQuantity(productDTO.getQuantity());
            product.setAvailability(productDTO.getQuantity() > 0);
            product.setAuthors(productMapper.fromListAuthorDTO(productDTO.getAuthors()));
            return productRepository.save(product);
        } else {
            return null;
        }
    }

    public Customer createCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.transform(customerDTO);
        hashPassword(customer);
        customer.setRole(Role.CUSTOMER);
        customer.setStatus(Status.ACTIVE);
        customerService.generateCardNumber(customer);
        return customerRepository.save(customer);
    }


    @Transactional
    public void hashPassword(Customer customer) {
        int dogIndex = customer.getEmail().indexOf("@");
        customer.setPassword(passwordEncoder.encode(customer.getEmail().substring(0, dogIndex)));
    }

    public List<OrderDTO> getPreparingOrders() {
        List<Order> preparingOrders = orderRepository.findByOrderStatus(OrderStatus.PREPARING);
        return orderMapper.transform(preparingOrders);
    }

    public List<OrderDTO> getIsReadyOrders() {
        List<Order> preparingOrders = orderRepository.findByOrderStatus(OrderStatus.IS_READY);
        return orderMapper.transform(preparingOrders);
    }

    @Transactional
    public Order makeOrderIsReady(String sellerId, String orderId) {
        Seller seller = sellerMainRepository.findById(sellerId).orElse(null);
        if (seller != null) {
            Order order = orderRepository.findById(orderId).orElse(null);
            if (order != null) {
                order.setSellerPreparing(seller);
                order.setOrderStatus(OrderStatus.IS_READY);
                setPINToOrder(order);

                return order;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private void setPINToOrder(Order order) {
        Random random = new Random();
        int pin = 1000 + random.nextInt(9000);
        order.setPin(pin);
    }

    @Transactional
    public Order makeOrderFinished(String sellerId, String orderId, int pin) {
        Seller seller = sellerMainRepository.findById(sellerId).orElse(null);
        if (seller != null) {
            Order order = orderRepository.findById(orderId).orElse(null);
            assert order != null;
            if (checkCorrectPIN(order, pin)) {
                order.setSellerFinished(seller);
                order.setOrderStatus(OrderStatus.RECEIVED);

                List<Product> productList = order.getProductList();
                Customer customer = customerMainRepository.findById(order.getCustomer().getId()).orElse(null);
                assert customer != null;
                customer.setLibraries(new ArrayList<>(productList));
                customerMainRepository.save(customer);

                return orderRepository.save(order);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private boolean checkCorrectPIN(Order order, int pin) {
        return (order.getPin() == pin);
    }

    public OrderDTO getOrderByTrackNumber(String trackNumber) {
        Order order = orderRepository.findByTrackNumberAndOrderStatus(trackNumber, OrderStatus.IS_READY).orElse(null);
        if (order != null) {
            return orderMapper.transform(order);
        } else {
            return null;
        }
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerMainRepository.findAll();
        return customerMapper.transform(customers);
    }

    @Transactional
    public Customer editCustomer(String customerId, CustomerDTO customerDTO) {
        Customer customer = customerMainRepository.findById(customerId).orElse(null);
        if (customer != null) {
            customer.setName(customerDTO.getName());
            customer.setPatronymic(customerDTO.getPatronymic());
            customer.setSurname(customerDTO.getSurname());
            customer.setPhone(customerDTO.getPhone());
            return customerMainRepository.save(customer);
        } else {
            return null;
        }
    }

    public List<AuthorDTO> getAllAuthors() {
        return authorMapper.transformFromList(authorRepository.findAll());
    }

    @Transactional
    public Author createAuthor(AuthorDTO authorDTO) {
        Author author = authorMapper.transform(authorDTO);
        return authorRepository.save(author);
    }
}
