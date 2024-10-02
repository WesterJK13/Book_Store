package rps.osipova.bookstore.Bookstore.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rps.osipova.bookstore.Bookstore.models.User;
import rps.osipova.bookstore.Bookstore.repository.UserMainRepository;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMainRepository userMainRepository;

    @Autowired
    public UserDetailsServiceImpl(UserMainRepository userMainRepository) {
        this.userMainRepository = userMainRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userMainRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User does not exist"));
        return SecurityUser.fromUser(user);
    }
}
