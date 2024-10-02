package rps.osipova.bookstore.Bookstore.models;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ADMIN(Set.of(Permission.SELLER_CRUD, Permission.PRODUCT_CRUD, Permission.PRODUCT_WORK)),
    SELLER(Set.of(Permission.PRODUCT_CRUD, Permission.PRODUCT_WORK)),
    CUSTOMER(Set.of(Permission.PRODUCT_WORK));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    //Реализация маппера для перевода пермишена в SimpleGrantedAuthority, который необходим для SpringSecurity
    //GrantedAuthority будет использовать данный SimpleGrantedAuthority дял хранения коллекции прав пользователя
    public Set<SimpleGrantedAuthority> getAuthority() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
