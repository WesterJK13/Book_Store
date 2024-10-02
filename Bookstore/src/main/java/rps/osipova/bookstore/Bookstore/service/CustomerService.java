package rps.osipova.bookstore.Bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rps.osipova.bookstore.Bookstore.dto.CustomerDTO;
import rps.osipova.bookstore.Bookstore.dto.OrderDTO;
import rps.osipova.bookstore.Bookstore.dto.ProductDTO;
import rps.osipova.bookstore.Bookstore.dto.ReviewDTO;
import rps.osipova.bookstore.Bookstore.mapper.CustomerMapper;
import rps.osipova.bookstore.Bookstore.mapper.OrderMapper;
import rps.osipova.bookstore.Bookstore.mapper.ProductMapper;
import rps.osipova.bookstore.Bookstore.mapper.ReviewMapper;
import rps.osipova.bookstore.Bookstore.models.*;
import rps.osipova.bookstore.Bookstore.repository.*;
import rps.osipova.bookstore.Bookstore.repository.RegistrAndAuth.CustomerRepository;

import java.util.*;

@Service
public class CustomerService {

    private final CustomerMainRepository customerMainRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomerMapper customerMapper;
    private final ApplicationRepository applicationRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Autowired
    public CustomerService(CustomerMainRepository customerMainRepository, CustomerRepository customerRepository, ProductRepository productRepository, ProductMapper productMapper, OrderRepository orderRepository, OrderMapper orderMapper, CustomerMapper customerMapper, ApplicationRepository applicationRepository, ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.customerMainRepository = customerMainRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.customerMapper = customerMapper;
        this.applicationRepository = applicationRepository;
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    public Customer findByCardNumber(String cardNumber) {
        return customerMainRepository.findByCardNumber(cardNumber).orElse(null);
    }

    public void generateCardNumber(Customer customer) {
        long number;
        do {
            number = generateUniqueCardNumber();
        } while (!isCardNumberExists(Long.toString(number)));

        customer.setCardNumber(Long.toString(number));
    }

    private long generateUniqueCardNumber() {
        Random random = new Random();
        return 1000000000L + (long) (random.nextDouble() * 9000000000L);
    }

    private boolean isCardNumberExists(String cardNumber) {
        Customer customer = findByCardNumber(cardNumber);
        return customer == null;
    }

    @Transactional
    public boolean addInBasket(String customerId, String productId) {
        Customer customer = customerMainRepository.findById(customerId).orElse(null);
        if (customer != null) {
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                List<Product> products = customer.getBasket();
                products.add(product);
                customer.setBasket(products);
                customerMainRepository.save(customer);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Transactional
    public boolean addInFavourites(String customerId, String productId) {
        Customer customer = customerMainRepository.findById(customerId).orElse(null);
        if (customer != null) {
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                List<Product> products = customer.getFavourites();
                products.add(product);
                customer.setFavourites(products);
                customerMainRepository.save(customer);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public List<ProductDTO> getProductsFromBasket(String customerId) {
        Customer customer = customerMainRepository.findById(customerId).orElse(null);
        if (customer != null) {
            List<Product> basket = customer.getBasket();
            if (basket != null) {
                return productMapper.transformFromProductList(basket);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public List<ProductDTO> getProductsFromFavourites(String customerId) {
        Customer customer = customerMainRepository.findById(customerId).orElse(null);
        if (customer != null) {
            List<Product> favourites = customer.getFavourites();
            if (favourites != null) {
                return productMapper.transformFromProductList(favourites);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Transactional
    public boolean deleteProductFromBasket(String customerId, String productId) {
        Customer customer = customerMainRepository.findById(customerId).orElse(null);
        if (customer != null) {
            List<Product> basket = customer.getBasket();
            basket = deleteElementFromProductList(basket, productId);
            customer.setBasket(basket);
            customerMainRepository.save(customer);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public boolean deleteProductFromFavourites(String customerId, String productId) {
        Customer customer = customerMainRepository.findById(customerId).orElse(null);
        if (customer != null) {
            List<Product> favourites = customer.getFavourites();
            favourites = deleteElementFromProductList(favourites, productId);
            customer.setFavourites(favourites);
            customerMainRepository.save(customer);
            return true;
        } else {
            return false;
        }
    }

    public List<Product> deleteElementFromProductList(List<Product> productList, String productId) {
        productList.removeIf(product -> Objects.equals(product.getId(), productId));
        return productList;
    }

    public boolean checkAvailableProduct(String productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            return product.getQuantity() > 0;
        } else {
            return true;
        }
    }

    public boolean checkSuchItemInFavourites(String customerId, String productId) {
        Customer customer = customerMainRepository.findById(customerId).orElse(null);
        List<Product> favourites = customer.getFavourites();
        for (Product product: favourites) {
            if (Objects.equals(product.getId(), productId)) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public Order registerOrder(String customerId) {
        Customer customer = customerMainRepository.findById(customerId).orElse(null);
        if (customer != null) {
            List<Product> productList = new ArrayList<>(customer.getBasket());

            List<Product> disavailableProductInBasket = new ArrayList<>();

            for (Product product: productList) {
                if (product.isAvailability()) {
                    Product product1 = productRepository.findById(product.getId()).orElse(null);
                    assert product1 != null;
                    if (product1.getQuantity() > 1) {
                        product1.setQuantity(product1.getQuantity() - 1);
                    } else {
                        product1.setQuantity(0);
                        product1.setAvailability(false);
                    }
                } else {
//                    productList.remove(product);
                    disavailableProductInBasket.add(product);
                }
            }

            for (Product prod: disavailableProductInBasket) {
                productList.removeIf(product -> Objects.equals(product.getId(), prod.getId()));
            }

            if (productList.size() == 0) {
                return null;
            } else {
                //Обнуление корзины у покупателя
                customer.setBasket(disavailableProductInBasket);

                Order order = new Order();
                order.setProductList(productList);
                order.setCustomer(customer);
                settingTrackNumberForOrder(order);
                settingTotalPriceForOrder(order, productList);
                order.setOrderStatus(OrderStatus.PREPARING);

                order = orderRepository.save(order);
                customerMainRepository.save(customer);

                return order;
            }
        } else {
            return null;
        }
    }

    private void settingTotalPriceForOrder(Order order, List<Product> productList) {
        int totalPrice = 0;
        for (Product product: productList) {
            totalPrice += product.getPrice();
        }
        order.setTotalPrice(totalPrice);
    }

    public void settingTrackNumberForOrder(Order order) {
        long number;
        do {
            number = generateUniqueTrackNumber();
        } while (!isTrackNumberExists(Long.toString(number)));

        order.setTrackNumber(Long.toString(number));
    }

    private long generateUniqueTrackNumber() {
        Random random = new Random();
        return 1000000000L + (long) (random.nextDouble() * 9000000000L);
    }

    private boolean isTrackNumberExists(String trackNumber) {
        Order order = findByTrackNumber(trackNumber);
        return order == null;
    }

    public Order findByTrackNumber(String trackNumber) {
        return orderRepository.findByTrackNumber(trackNumber).orElse(null);
    }


    public List<CustomerDTO> findAllCustomersWithOutMe(String myId) {
        return customerMapper.transform(customerMainRepository.findAllExcept(myId));
    }

    public List<CustomerDTO> findAllCustomers() {
        return customerMapper.transform(customerMainRepository.findAll());
    }

    @Transactional
    public Application sendApplicationToFriend(String myId, String friendId) {
        Customer me = customerMainRepository.findById(myId).orElse(null);
        if (me != null) {
            Customer friend = customerMainRepository.findById(friendId).orElse(null);
            if (friend != null) {
                Application application = new Application();
                application.setApplicant(me);
                application.setFutureFriend(friend);
                application.setApplicationStatus(ApplicationStatus.NEW);
                return applicationRepository.save(application);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public List<Application> getAllApplications(String myId) {
        Customer customer = customerMainRepository.findById(myId).orElse(null);
        return applicationRepository.findAllByFutureFriendAndApplicationStatus(customer, ApplicationStatus.NEW);
    }

    /**
     * Метод для отклонения заявки, который просто переводит Application в статус REJECT
     */
    @Transactional
    public void rejectApplication(String applicationId) {
        Application application = applicationRepository.findById(applicationId).orElse(null);
        if (application != null) {
            application.setApplicationStatus(ApplicationStatus.REJECT);
            applicationRepository.save(application);
        }
    }

    /**
     * Метод, который переводит заявку в статус APPROVED и при этом добавляем пользователей из заявки друг другу в друзья!!!
     */
    @Transactional
    public Application approveApplication(String applicationId) {
        Application application = applicationRepository.findById(applicationId).orElse(null);
        if (application != null) {
            application.setApplicationStatus(ApplicationStatus.APPROVED);

            //Назначение в друзья
            String friend1Id = application.getApplicant().getId();
            String friend2Id = application.getFutureFriend().getId();
            Customer friend1 = customerMainRepository.findById(friend1Id).orElse(null);
            Customer friend2 = customerMainRepository.findById(friend2Id).orElse(null);
            List<Customer> friendsCustomer1 = friend1.getFriends();
            friendsCustomer1.add(friend2);
            friend1.setFriends(friendsCustomer1);
            customerMainRepository.save(friend1);

            return applicationRepository.save(application);
        } else {
            return null;
        }
    }

    public List<Customer> getAllFriends(String myId) {
        List<Customer> friends1 = customerMainRepository.findAllFriends(myId);
        List<Customer> friends2 = customerMainRepository.findAllCustomerByFriends(myId);

        List<Customer> friends = new ArrayList<>(friends1);
        friends.addAll(friends2);
        return friends;
    }

    public List<Customer> getAllNotFriends(String myId) {
        List<Customer> friends = customerMainRepository.findAllCustomerNotConnected(myId);
        return friends;
    }

    public List<Product> getAllProductsInLibrary(String myId) {
        Customer customer = customerMainRepository.findById(myId).orElse(null);
        if (customer != null) {
            List<Product> libraries = customer.getLibraries();
            return libraries;
        } else {
            return null;
        }
    }

    @Transactional
    public Review writeReview(String customerId, String productId, ReviewDTO reviewDTO) {
        Customer customer = customerMainRepository.findById(customerId).orElse(null);
        if (customer != null) {
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                Review review = reviewMapper.transform(reviewDTO);
                review.setCustomer(customer);
                review.setProduct(product);
                if (review.getCommentForAll() != null && review.getCommentForFriends() != null) {
                    review.setReviewDateForAll(new Date());
                    review.setReviewDateForFriends(new Date());
                } else if (review.getCommentForAll() != null){
                    review.setReviewDateForAll(new Date());
                } else {
                    review.setReviewDateForFriends(new Date());
                }
                return reviewRepository.save(review);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Transactional
    public void deleteReview(String reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public Customer getMyProfile(String myId) {
        return customerMainRepository.findById(myId).orElse(null);
    }

    public List<OrderDTO> getMyOrders(String customerId) {
        Customer customer = customerMainRepository.findById(customerId).orElse(null);
        if (customer != null) {
//            return orderMapper.transform(orderRepository.findAllByCustomer(customer));
            return orderMapper.transform(orderRepository.findAllByCustomer(customer, Sort.by(Sort.Direction.DESC, "dateCreate")));
        } else {
            return null;
        }
    }

    public List<ReviewDTO> getReviewForProductByFriends(String productId, String customerId) {
        Product product = productRepository.findById(productId).orElse(null);
        Customer me = customerMainRepository.findById(customerId).orElse(null);
        List<Customer> friends = getAllFriends(customerId);
        friends.add(me);
        List<Review> response = new ArrayList<>();
        for (Customer friend: friends) {
            List<Review> reviews = reviewRepository.findAllByProductAndCustomer(product, friend);
            response.addAll(reviews);
        }


        return (reviewMapper.transform(response));
    }

    public boolean deleteFriend(String myId, String friendId) {
        Customer me = customerMainRepository.findById(myId).orElse(null);
        Customer friend = customerMainRepository.findById(friendId).orElse(null);

        if (me != null && friend != null) {
            me.getFriends().remove(friend);
            friend.getFriends().remove(me);

            customerMainRepository.save(me);
            customerMainRepository.save(friend);

            return true;
        } else {
            return false;
        }

    }
}
