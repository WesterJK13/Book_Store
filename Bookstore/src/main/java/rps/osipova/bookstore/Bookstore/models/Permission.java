package rps.osipova.bookstore.Bookstore.models;

public enum Permission {
    SELLER_CRUD("seller:crud"),
    PRODUCT_CRUD("product:crud"),
    PRODUCT_WORK("product:work");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
