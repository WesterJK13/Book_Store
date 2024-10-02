package rps.osipova.bookstore.Bookstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rps.osipova.bookstore.Bookstore.dto.SellerDTO;
import rps.osipova.bookstore.Bookstore.dto.UserDTO;
import rps.osipova.bookstore.Bookstore.mapper.SellerMapper;
import rps.osipova.bookstore.Bookstore.models.Seller;
import rps.osipova.bookstore.Bookstore.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;
    private final SellerMapper sellerMapper;

    @Autowired
    public AdminController(AdminService adminService, SellerMapper sellerMapper) {
        this.adminService = adminService;
        this.sellerMapper = sellerMapper;
    }

    /**
     * Метод для просмотра списка всех продавцов
     */
    @GetMapping("/sellers")
    public List<SellerDTO> getSellers() {
        return adminService.getSellers();
    }


    @GetMapping("getSeller")
    public ResponseEntity<?> getSeller(@RequestParam String sellerId) {
        if (sellerId != null) {
            SellerDTO sellerDTO = sellerMapper.transform(adminService.getSellerById(sellerId));
            if (sellerDTO != null) {
                return new ResponseEntity<>(sellerDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Невозможно получить данные продовца", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Невозможно получить данные продовца", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Метод для создания продавца
     */
    @PostMapping("/createSeller")
    public ResponseEntity<?> createSeller(@RequestBody UserDTO sellerDTO) {
        if (sellerDTO != null) {
            SellerDTO response = adminService.createSeller(sellerDTO);
            if (response != null) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Не удалось создать продавца, т.к. были заданы неверные параметры.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Не удалось создать продавца, т.к. были заданы неверные параметры.", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Метод для редактирования продавца
     */
    @PutMapping("/editSeller")
    public ResponseEntity<?> editSeller(@RequestParam String sellerId,
                                        @RequestBody UserDTO userDTO) {
        if (sellerId != null && userDTO != null) {
            Seller seller = adminService.editSeller(sellerId, userDTO);
            if (seller != null) {
                return new ResponseEntity<>("Данные продавца успешно отредактированы.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Не удалось изменить данные продавца, т.к. переданы некорректные параметры.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Не удалось изменить данные продавца, т.к. переданы некорректные параметры.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Метод для удаления продавца
     */
    @PostMapping("/deleteSeller")
    public ResponseEntity<?> deleteSeller(@RequestParam String sellerId) {
        if (sellerId != null) {
            adminService.deleteSeller(sellerId);
            Seller seller = adminService.checkSeller(sellerId);
            if (seller == null) {
                return new ResponseEntity<>("Продавец успешно удален.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Был передан некорретный параметр - id для удаления.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Был передан некорретный параметр - id для удаления.", HttpStatus.BAD_REQUEST);
        }
    }


}
