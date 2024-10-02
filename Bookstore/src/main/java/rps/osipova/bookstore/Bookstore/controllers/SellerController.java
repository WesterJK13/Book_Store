package rps.osipova.bookstore.Bookstore.controllers;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rps.osipova.bookstore.Bookstore.dto.*;
import rps.osipova.bookstore.Bookstore.mapper.OrderMapper;
import rps.osipova.bookstore.Bookstore.mapper.ProductMapper;
import rps.osipova.bookstore.Bookstore.mapper.SellerMapper;
import rps.osipova.bookstore.Bookstore.models.*;
import rps.osipova.bookstore.Bookstore.service.GeneralService;
import rps.osipova.bookstore.Bookstore.service.SellerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seller")
public class SellerController {

    private final SellerMapper sellerMapper;
    private final SellerService sellerService;
    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;
    private final GeneralService generalService;

    @Autowired
    public SellerController(SellerMapper sellerMapper, SellerService sellerService, OrderMapper orderMapper, ProductMapper productMapper, GeneralService generalService) {
        this.sellerMapper = sellerMapper;
        this.sellerService = sellerService;
        this.orderMapper = orderMapper;
        this.productMapper = productMapper;
        this.generalService = generalService;
    }

    /**
     * Создание товара
     */
    @PostMapping("/createProduct")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO product = sellerService.createProduct(productDTO);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Удаление товара по ID товара
     */
    @PostMapping("/deleteProduct")
    public ResponseEntity<?> deleteProduct(@RequestParam String productId) {
        if (productId != null) {
            sellerService.deleteProduct(productId);
            return new ResponseEntity<>("Товар успешно удален.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Был передан некорректный параметр для удаления товара.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Редактирование товара по ID товара
     */
    @PutMapping("/editProduct")
    public ResponseEntity<?> editProduct(@RequestParam String productId, @RequestBody ProductDTO productDTO) {
        if (productId != null && productDTO != null) {
            Product product = sellerService.editProduct(productId, productDTO);
            if (product != null) {
                ProductDTO response = productMapper.transform(product);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Были переданы некорректные входные параметры для изменения товара.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Были переданы некорректные входные параметры для изменения товара.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/createAuthor")
    public ResponseEntity<?> createAuthor(@RequestBody AuthorDTO authorDTO) {
        if (authorDTO != null) {
            Author author = sellerService.createAuthor(authorDTO);
            if (author != null) {
               return new ResponseEntity<>("Автор успешно добавлен в систему", HttpStatus.OK);
             } else {
                return new ResponseEntity<>("Невозможно создать автора, т.к. вы не передали параметры.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Невозможно создать автора, т.к. вы не передали параметры.", HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/allCustomers")
    public ResponseEntity<?> getAllCustomers() {
        List<CustomerDTO> customerDTOList = sellerService.getAllCustomers();
        return new ResponseEntity<>(customerDTOList, HttpStatus.OK);
    }

    /**
     * Создание профиля покупателя
     */
    @PostMapping("/createCustomer")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = sellerService.createCustomer(customerDTO);
        if (customer != null) {
            return new ResponseEntity<>("Пользователь успешно создан.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Не удалось создать пользователя, т.к. были переданы неверные параметры.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Метод для редактирования продавца
     */
    @PutMapping("/editCustomer")
    public ResponseEntity<?> editSeller(@RequestParam String customerId,
                                        @RequestBody CustomerDTO customerDTO) {
        if (customerId != null && customerDTO != null) {
            Customer customer = sellerService.editCustomer(customerId, customerDTO);
            if (customer != null) {
                return new ResponseEntity<>("Данные пользователя успешно отредактированы.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Не удалось изменить данные продавца, т.к. переданы некорректные параметры.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Не удалось изменить данные продавца, т.к. переданы некорректные параметры.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Просмотр всех заказов, где статус PREPARING (т.е. список заказов, которые требуют сборки и подтверждения готовности (перевода в статус IS_READY)
     */
        @GetMapping("/preparingOrders")
    public ResponseEntity<?> getPreparingOrders() {
        List<OrderDTO> preparingOrders = sellerService.getPreparingOrders();
        if (preparingOrders.size() > 0) {
            return new ResponseEntity<>(preparingOrders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("На данный момент заказы для сборки отсутствуют.", HttpStatus.OK);
        }
    }

    /**
     * Просмотр всех заказов, где статус IS_READY (т.е. список заказов, которые готовы к выдаче покупателю)
     */
    @GetMapping("/isReadyOrders")
    public ResponseEntity<?> getIsReadyOrders() {
        List<OrderDTO> preparingOrders = sellerService.getIsReadyOrders();
        if (preparingOrders.size() > 0) {
            return new ResponseEntity<>(preparingOrders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("На данный момент заказы для выдачи отсутствуют.", HttpStatus.OK);
        }
    }

    /**
     * Просмотр заказа по ТрекНомеру, где статус IS_READY
     */
    @GetMapping("/isReadyOrder")
    public ResponseEntity<?> isReadyOrder(@RequestParam String trackNumber) {
        OrderDTO response = sellerService.getOrderByTrackNumber(trackNumber);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Заказ с указанным трек-номером не найден.", HttpStatus.OK);
        }
    }


    /**
     * Сборка заказа и перевод заказа (Order) в статус IS_READY
     */
    @PostMapping("/makeOrderIsReady")
    public ResponseEntity<?> makeOrderIsReady(@RequestParam String sellerId, @RequestParam String orderId) {
        if (sellerId != null && orderId != null) {
            Order order = sellerService.makeOrderIsReady(sellerId, orderId);
            if (order != null) {
                OrderDTO response = orderMapper.transform(order);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Невозможно подтвердить готовность заказа, т.к. переданы некорректные параметры запроса.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Невозможно подтвердить готовность заказа, т.к. переданы некорректные параметры запроса.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Оформить покупку покупателя (т.е. ситуация, когда покупатель уже пришел в магазин и забирает свой заказ
     * тут прдоавец просто нажимает кнопку подтверждения, что покупатель забирает товар
     * При этом на фронте это реализовано так: Продавец нажимает на Заказе кнопку "Выдать товар", ему высвечивается окно, где надо ввести PIN подтверждения
     * Покупатель диктует PIN, который он видит у себя на странице и дальше продавец вводит его и нажимает подтвердить
     * Именно кнопка подтвердить обращается к данному методу контроллера (т.е. входным параметром является SellerId, OrderId, PIN
     */
    @PostMapping("/makeOrderFinished")
    public ResponseEntity<?> makeOrderFinished(@RequestParam String sellerId, @RequestParam String orderId, @RequestParam int pin) {
        if (sellerId != null && orderId != null && pin > 999 && pin < 10000) {
            Order order = sellerService.makeOrderFinished(sellerId, orderId, pin);
            if (order != null){
                return new ResponseEntity<>(orderMapper.transform(order), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Не удалось оформить заказ, т.к. переданы некорректные параметры.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Не удалось оформить заказ, т.к. переданы некорректные параметры.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllAuthors")
    public ResponseEntity<?> getAllAuthors() {
        List<AuthorDTO> authorDTOS = sellerService.getAllAuthors();
        return new ResponseEntity<>(authorDTOS, HttpStatus.OK);
    }

}
