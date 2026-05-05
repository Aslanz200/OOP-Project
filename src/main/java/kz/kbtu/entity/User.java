package kz.kbtu.entity;

import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

public interface User<T extends Role> extends AutoCloseable {
    UUID id();
    String username();
    Iterable<String> fullName();

    Optional<T> role();

    boolean checkPassword(String password);

    interface UserBuilder<T extends Role> {
        UserBuilder<T> username(String name);
        UserBuilder<T> password(String password);
        <R extends Role> UserBuilder<R> role(Role.RoleBuilder<R> builder);

        UserBuilder<T> fullName(String... names);
        UserBuilder<T> fullName(Iterable<String> names);

        User<T> build();
    }
}
