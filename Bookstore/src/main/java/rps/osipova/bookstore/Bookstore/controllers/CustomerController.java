package rps.osipova.bookstore.Bookstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rps.osipova.bookstore.Bookstore.dto.CustomerDTO;
import rps.osipova.bookstore.Bookstore.dto.OrderDTO;
import rps.osipova.bookstore.Bookstore.dto.ProductDTO;
import rps.osipova.bookstore.Bookstore.dto.ReviewDTO;
import rps.osipova.bookstore.Bookstore.mapper.ApplicationMapper;
import rps.osipova.bookstore.Bookstore.mapper.CustomerMapper;
import rps.osipova.bookstore.Bookstore.mapper.ProductMapper;
import rps.osipova.bookstore.Bookstore.models.*;
import rps.osipova.bookstore.Bookstore.repository.CustomerMainRepository;
import rps.osipova.bookstore.Bookstore.repository.ProductRepository;
import rps.osipova.bookstore.Bookstore.repository.RegistrAndAuth.CustomerRepository;
import rps.osipova.bookstore.Bookstore.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final CustomerMainRepository customerMainRepository;
    private final CustomerMapper customerMapper;
    private final ApplicationMapper applicationMapper;

    @Autowired
    public CustomerController(ProductMapper productMapper, ProductRepository productRepository,
                              CustomerService customerService, CustomerRepository customerRepository,
                              CustomerMainRepository customerMainRepository, CustomerMapper customerMapper, ApplicationMapper applicationMapper) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.customerService = customerService;
        this.customerRepository = customerRepository;
        this.customerMainRepository = customerMainRepository;
        this.customerMapper = customerMapper;
        this.applicationMapper = applicationMapper;
    }

    /**
     * Добавление товара в корзину
     */
    @PostMapping("/addInBasket")
    public ResponseEntity<?> addInBasket(@RequestParam String customerId, @RequestParam String productId) {
        if (customerId != null && productId != null) {
            boolean checkAvailable = customerService.checkAvailableProduct(productId);
            if (!checkAvailable) {
                return new ResponseEntity<>("Товар отсутствует в наличии, в связи с чем его невозможно добавить в корзину. Вы можете добавить данный товар в Избранное.", HttpStatus.OK);
            } else {
                boolean flag = customerService.addInBasket(customerId, productId);
                if (flag) {
                    return new ResponseEntity<>("Товар успешно добавлен в корзину.", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Не удалось добавить товар в корзину, т.к. были переданы некорректные параметры запроса.", HttpStatus.BAD_REQUEST);
                }
            }
        } else {
            return new ResponseEntity<>("Не удалось добавить товар в корзину, т.к. были переданы некорректные параметры запроса.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Получение всех товаров из Корзины
     */
    @GetMapping("getProductsFromBasket")
    public ResponseEntity<?> getProductsFromBasket(@RequestParam String customerId) {
        if (customerId != null) {
            List<ProductDTO> productList = customerService.getProductsFromBasket(customerId);
            if (productList != null) {
                return new ResponseEntity<>(productList, HttpStatus.OK);
            } else {
                return null;
            }
        } else {
            return new ResponseEntity<>("Не удалось получить данные о товарах в корзине с сервера.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Удаление товара из Корзины
     */
    @PostMapping("/deleteProductFromBasket")
    public ResponseEntity<?> deleteProductFromBasket(@RequestParam String customerId, @RequestParam String productId) {
        if (customerId != null && productId != null) {
            boolean flag = customerService.deleteProductFromBasket(customerId, productId);
            if (flag) {
                return new ResponseEntity<>("Товар успешно удален из Корзины", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Не удалось удалить товар из корзины, т.к. были переданы неверные параметры.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Не удалось удалить товар из корзины, т.к. были переданы неверные параметры.", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Добавление товара в избранное
     */
    @PostMapping("addInFavourites")
    public ResponseEntity<?> addInFavourites(@RequestParam String customerId, @RequestParam String productId) {
        if (customerId != null && productId != null) {
            boolean checkFlag = customerService.checkSuchItemInFavourites(customerId, productId);
            if (checkFlag) {
                return new ResponseEntity<>("Товар уже добавлен в Избранное", HttpStatus.OK);
            } else {
                boolean flag = customerService.addInFavourites(customerId, productId);
                if (flag) {
                    return new ResponseEntity<>("Товар успешно добавлен в избранное.", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Не удалось добавить товар в избранное, т.к. были переданы некорректные параметры запроса.", HttpStatus.BAD_REQUEST);
                }
            }
        } else {
            return new ResponseEntity<>("Не удалось добавить товар в избранное, т.к. были переданы некорректные параметры запроса.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Получение всех товаров из Избранного
     */
    @GetMapping("getProductsFromFavourites")
    public ResponseEntity<?> getProductsFromFavourites(@RequestParam String customerId) {
        if (customerId != null) {
            List<ProductDTO> productList = customerService.getProductsFromFavourites(customerId);
            if (productList != null) {
                return new ResponseEntity<>(productList, HttpStatus.OK);
            } else {
                return null;
            }
        } else {
            return new ResponseEntity<>("Не удалось получить данные о товарах в корзине с сервера.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Удаление товара из Избранного
     */
    @PostMapping("/deleteProductFromFavourites")
    public ResponseEntity<?> deleteProductFromFavourites(@RequestParam String customerId, @RequestParam String productId) {
        if (customerId != null && productId != null) {
            boolean flag = customerService.deleteProductFromFavourites(customerId, productId);
            if (flag) {
                return new ResponseEntity<>("Товар успешно удален из с", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Не удалось удалить товар из Избранного, т.к. были переданы неверные параметры.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Не удалось удалить товар из Избранного, т.к. были переданы неверные параметры.", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Оформление заказа
     * Выполняется формирование заказа, подсчет полной стоимости заказа (сумма цен товаров в Корзине)
     * Также после оформления автоматически должно сформироваться ожидание подтверждения готовности сборки от Продавца
     */
    @PostMapping("/registerOrder")
    public ResponseEntity<?> registerOrder(@RequestParam String customerId) {
        if (customerId != null) {
            Order order = customerService.registerOrder(customerId);
            if (order != null) {
                return new ResponseEntity<>("Заказ успешно оформлен и находится на странице Заказы.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Не удалось оформить заказ, т.к. был передан некорректный параметр.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Не удалось оформить заказ, т.к. был передан некорректный параметр.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/myOrders")
    public ResponseEntity<?> getMyOrders(@RequestParam String customerId) {
        if (customerId != null) {
            List<OrderDTO> orderDTOList = customerService.getMyOrders(customerId);
            return new ResponseEntity<>(orderDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Невозможно получить данные о заказах, т.к. не переданы корректные параметры.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Добавление друга (т.е. когда мы листаем список пользователей, мы можем на конкретном юзере нажать + и будет отправлен запрос в друзья
     */
    @PostMapping("/sendApplicationToFriend")
    public ResponseEntity<?> sendApplicationToFriend(@RequestParam String myId, @RequestParam String friendId) {
        if (myId != null && friendId != null) {
            Application application = customerService.sendApplicationToFriend(myId, friendId);
            if (application != null) {
                return new ResponseEntity<>(applicationMapper.transform(application), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Невозможно отправить заявку в друзья, т.к. переданы некорректные параметры.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Невозможно отправить заявку в друзья, т.к. переданы некорректные параметры.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Метод для получения всех заявок в друзья
     */
    @GetMapping("/allApplications")
    public ResponseEntity<?> getAllApplications(@RequestParam String myId) {
        if (myId != null) {
            List<Application> applications = customerService.getAllApplications(myId);
            return new ResponseEntity<>(applicationMapper.transformFromList(applications), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Невозможно получить заявки в друзья, т.к. передан некорректный id пользователя.", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Метод, который отвечает за принятие или отклонение заявки в друзья
     */
    @PostMapping("/replyToApplication")
    public ResponseEntity<?> replyToApplication(@RequestParam String applicationId, @RequestParam String response) {
        if (applicationId != null && response != null) {
            if (response.equals("yes")) {
                Application application = customerService.approveApplication(applicationId);
                if (application != null) {
                    return new ResponseEntity<>("Заявка одобрена, теперь у вас есть еще один друг.", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Невозможно обработать ответ, т.к. не переданы корректные параметры", HttpStatus.BAD_REQUEST);
                }
            } else {
                customerService.rejectApplication(applicationId);
                return new ResponseEntity<>("Заявка отклонена", HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Невозможно обработать ответ, т.к. не переданы корректные параметры", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/deleteFriend")
    public ResponseEntity<?> deleteFriend(@RequestParam String myId, @RequestParam String friendId) {
        if (myId != null && friendId != null) {
            boolean response = customerService.deleteFriend(myId, friendId);
            if (response) {
                return new ResponseEntity<>("Пользователь удален из ваших друзей.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Невозможно удалить друга, т.к. переданы некорректные параметры.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Невозможно удалить друга, т.к. переданы некорректные параметры.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Поиск всех друзей (данному списку людей нельзя отправить заявку в друзья)
     */
    @GetMapping("/allFriends")
    public ResponseEntity<?> getAllFriends(@RequestParam String myId) {
        if (myId != null) {
            List<Customer> customerList = customerService.getAllFriends(myId);
            return new ResponseEntity<>(customerMapper.transform(customerList), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Невозможно найти друзей, т.к. передан некорректный параметр.", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Поиск всех не друзей (то есть пользователей, которые будут отображаться в списке пользователей,
     * претендующих на добавление в друзья, то есть им можно отправлять заявку в друзья)
     */
    @GetMapping("/allNotFriends")
    public ResponseEntity<?> getAllNotFriends(@RequestParam String myId) {
        if (myId != null) {
            List<Customer> customerList = customerService.getAllNotFriends(myId);
            return new ResponseEntity<>(customerMapper.transform(customerList), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Передан некорректный параметр.", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Смотреть все книги, которые находятся у пользователя в библиотеке
     */
    @GetMapping("/allProductsInLibrary")
    public ResponseEntity<?> getAllProductsInLibrary(@RequestParam String myId) {
        if (myId != null) {
            List<Product> productList = customerService.getAllProductsInLibrary(myId);
            return new ResponseEntity<>(productMapper.transformFromProductList(productList), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Невозможно получить книги из вашей библиотеки, т.к. передан некорректный параметр.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Оставить отзыв. Логика такая, что у нас на фронте есть две кнопки:
     * 1) оставить отзыв для всех
     * 2) оставить отзыв для друзей
     * в зависимости от того, на какую кнопку мы нажимаем открываются различные формы и заполняются различные JSON для отправки на сервер (т.е. reviewDTO)
     * в случае выбора варианта 1 заполняется поле commentForAll, а если выбран вариант 2, то заполняется commentForFriends
     */
    @PostMapping("/writeReview")
    public ResponseEntity<?> writeReview(@RequestParam String customerId, @RequestParam String productId, @RequestBody ReviewDTO reviewDTO) {
        if (customerId != null && productId != null && reviewDTO != null) {
            Review review = customerService.writeReview(customerId, productId, reviewDTO);
            if (review != null) {
                return new ResponseEntity<>("Отзыв опубликован.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Не удалось создать отзыв, т.к. переданы некорректные параметры.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Не удалось создать отзыв, т.к. переданы некорректные параметры.", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Удаление отзыва по id
     */
    @PostMapping("/deleteReview")
    public ResponseEntity<?> deleteReview(@RequestParam String reviewId) {
        if (reviewId != null) {
            customerService.deleteReview(reviewId);
            return new ResponseEntity<>("Отзыв удален.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Невозможно выполнить удаление.", HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/reviewByFriendsForProduct")
    public ResponseEntity<?> getReviewByFriendsForProduct(@RequestParam String productId, @RequestParam String customerId) {
        if (productId != null && customerId != null) {
            List<ReviewDTO> response = customerService.getReviewForProductByFriends(productId, customerId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Невозможно получить отзывы друзей о товаре.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Получение данных для личного профиля
     */
    @GetMapping("/myProfile")
    public ResponseEntity<?> getMyProfile(@RequestParam String myId) {
        if (myId != null) {
            CustomerDTO customerDTO = customerMapper.transform(customerService.getMyProfile(myId));
            if (customerDTO.getId() != null) {
                return new ResponseEntity<>(customerDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Невозможно получить данные о пользователе.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Невозможно получить данные о пользователе.", HttpStatus.BAD_REQUEST);
        }
    }
}
