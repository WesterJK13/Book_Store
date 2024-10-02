package rps.osipova.bookstore.Bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rps.osipova.bookstore.Bookstore.dto.SellerDTO;
import rps.osipova.bookstore.Bookstore.dto.UserDTO;
import rps.osipova.bookstore.Bookstore.mapper.SellerMapper;
import rps.osipova.bookstore.Bookstore.models.*;
import rps.osipova.bookstore.Bookstore.repository.RegistrAndAuth.SellerRepository;
import rps.osipova.bookstore.Bookstore.repository.SellerMainRepository;

import java.util.List;

@Service
public class AdminService {

    private final SellerMapper sellerMapper;
    private final SellerRepository sellerRepository;
    private final SellerMainRepository sellerMainRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(SellerMapper sellerMapper, SellerRepository sellerRepository, SellerMainRepository sellerMainRepository, PasswordEncoder passwordEncoder) {
        this.sellerMapper = sellerMapper;
        this.sellerRepository = sellerRepository;
        this.sellerMainRepository = sellerMainRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<SellerDTO> getSellers() {
        return sellerMapper.transformListUser(sellerRepository.findAllByRole(Role.SELLER));
    }

    @Transactional
    public void deleteSeller(String sellerId) {
        sellerRepository.deleteById(sellerId);
    }

    public Seller checkSeller(String sellerId) {
        Seller seller = sellerMapper.transformFromUser(sellerRepository.findById(sellerId).orElse(null));
        return seller;
    }

    @Transactional
    public SellerDTO createSeller(UserDTO userDTO) {
        Seller seller = sellerMapper.transform(userDTO);
        if (seller != null) {
            seller.setRole(Role.SELLER);
            hashPassword(seller);
            seller.setStatus(Status.ACTIVE);
            Seller response = sellerRepository.save(seller);
            return sellerMapper.transform(response);
        } else {
            return null;
        }
    }

    @Transactional
    public void hashPassword(User user) {
        int dogIndex = user.getEmail().indexOf("@");
        user.setPassword(passwordEncoder.encode(user.getEmail().substring(0, dogIndex)));
    }

    public Seller editSeller(String sellerId, UserDTO userDTO) {
        Seller seller = sellerMapper.transformFromUser(sellerRepository.findById(sellerId).orElse(null));
        if (seller != null) {
            seller.setName(userDTO.getName());
            seller.setSurname(userDTO.getSurname());
            seller.setPatronymic(userDTO.getPatronymic());
            seller.setBirthDate(userDTO.getBirthDate());
            seller.setEmail(userDTO.getEmail());
            return sellerRepository.save(seller);
        } else {
            return null;
        }
    }


    public Seller getSellerById(String sellerId) {
        Seller seller = sellerMainRepository.findById(sellerId).orElse(null);
        return seller;
    }
}
