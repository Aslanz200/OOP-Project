package kz.kbtu.api;

public interface Role {
    Class<? extends Role> implementation();

    interface RoleBuilder<T extends Role> {
        Class<T> implementation();
        T build(User<T> user);
    }
}
