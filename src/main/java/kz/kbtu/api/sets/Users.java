package kz.kbtu.api.sets;

import kz.kbtu.api.Course;
import kz.kbtu.api.Role;
import kz.kbtu.api.User;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface Users<T extends Role> {

    Stream<User<T>> all();

    <Expect extends User<R>, R extends Role> Expect byRole(Class<R> roleClass,
                                                                     Class<Expect> expectedSet);

    /**
     * Unsafe cast
     */
    <R extends Role> Users<R> byRole(Class<T> roleClass);
    Users<T> participants(Course course);


    Optional<User<T>> fetch(UUID id);
    Optional<User<T>> fetch(String username);


    static <T extends Role> Users<T> ofEmpty() {
        return new Users<T>() {
            @Override
            public Stream<User<T>> all() {
                return Stream.empty();
            }

            @Override
            public <Expect extends User<R>, R extends Role> Expect byRole(Class<R> roleClass, Class<Expect> expectedSet) {
                return (Expect) Users.ofEmpty();
            }

            @Override
            public <R extends Role> Users<R> byRole(Class<T> roleClass) {
                return Users.ofEmpty();
            }

            @Override
            public Users<T> participants(Course course) {
                return Users.ofEmpty();
            }

            @Override
            public Optional<User<T>> fetch(UUID id) {
                return Optional.empty();
            }

            @Override
            public Optional<User<T>> fetch(String username) {
                return Optional.empty();
            }
        };
    }
}
