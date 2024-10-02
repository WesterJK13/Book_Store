package rps.osipova.bookstore.Bookstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import rps.osipova.bookstore.Bookstore.dto.LoginDTO;
import rps.osipova.bookstore.Bookstore.dto.SignUpDTO;
import rps.osipova.bookstore.Bookstore.dto.UserDTO;
import rps.osipova.bookstore.Bookstore.mapper.CustomerMapper;
import rps.osipova.bookstore.Bookstore.mapper.UserMapper;
import rps.osipova.bookstore.Bookstore.models.Customer;
import rps.osipova.bookstore.Bookstore.models.Role;
import rps.osipova.bookstore.Bookstore.models.Status;
import rps.osipova.bookstore.Bookstore.models.User;
import rps.osipova.bookstore.Bookstore.repository.RegistrAndAuth.CustomerRepository;
import rps.osipova.bookstore.Bookstore.repository.UserMainRepository;
import rps.osipova.bookstore.Bookstore.service.CustomerService;

@Controller
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserMainRepository userMainRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserMainRepository userMainRepository,
                          UserMapper userMapper, PasswordEncoder passwordEncoder, CustomerMapper customerMapper,
                          CustomerRepository customerRepository, CustomerService customerService) {
        this.authenticationManager = authenticationManager;
        this.userMainRepository = userMainRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticationUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userMainRepository.findByEmail(loginDTO.getEmail()).orElse(null);
        if (user != null) {
            UserDTO userDTO = userMapper.transform(user);

            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Please, check your email", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registrationUser(@RequestBody SignUpDTO signUpDTO) {
        if (userMainRepository.existsByEmail(signUpDTO.getEmail())) {
            return new ResponseEntity<>("This email address is already registered in the system.", HttpStatus.BAD_REQUEST);
        }

        Customer customer = customerMapper.transform(signUpDTO);
        customer.setRole(Role.CUSTOMER);
        customer.setStatus(Status.ACTIVE);
        customer.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        customerService.generateCardNumber(customer);

        customer = customerRepository.save(customer);
        UserDTO response = userMapper.transform(customer);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
