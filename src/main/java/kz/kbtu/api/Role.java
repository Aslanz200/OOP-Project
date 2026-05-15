package kz.kbtu.api;

import java.util.stream.Stream;

public interface Role {

    Stream<String> permissions();
    boolean can(String permission);

    Class<? extends Role> implementation();

    interface RoleBuilder<T extends Role> {
        Class<T> implementation();
        T build(User<T> user);
    }
}
