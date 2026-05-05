package kz.kbtu.entity.collection;

import kz.kbtu.entity.Role;
import kz.kbtu.entity.User;

import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface Users {

    Stream<User<?>> all();
    <T extends Role> Stream<User<T>> byRole(Class<T> impl);

    Optional<User<?>> byId(UUID id);
    Optional<User<?>> byUsername(String username);


    static Users ofEmpty() {
        return new Users() {
            @Override
            public Stream<User<?>> all() {
                return Stream.empty();
            }

            @Override
            public <T extends Role> Stream<User<T>> byRole(Class<T> impl) {
                return Stream.empty();
            }

            @Override
            public Optional<User<?>> byId(UUID id) {
                return Optional.empty();
            }

            @Override
            public Optional<User<?>> byUsername(String username) {
                return Optional.empty();
            }

        };
    }
}
