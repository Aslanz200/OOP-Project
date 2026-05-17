package kz.kbtu.api;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface User<T extends Role> {
    UUID id();
    String username();
    Stream<String> fullName();

    Optional<T> role();
    boolean is(Class<? extends Role> role);

    boolean checkPassword(String password);

    interface UserBuilder<T extends Role> {
        UserBuilder<T> username(String name);
        UserBuilder<T> password(String password);
        UserBuilder<T> password(String password, boolean hashed);
        <R extends Role> UserBuilder<R> role(Role.RoleBuilder<R> builder);

        UserBuilder<T> fullName(String... names);
        UserBuilder<T> fullName(Iterable<String> names);

        User<T> build();
    }
}
