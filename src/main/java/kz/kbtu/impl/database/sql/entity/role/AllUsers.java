package kz.kbtu.impl.database.sql.entity.role;

import kz.kbtu.api.Course;
import kz.kbtu.api.Role;
import kz.kbtu.api.User;
import kz.kbtu.api.sets.Users;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class AllUsers implements Users<Role> {
    @Override
    public Stream<User<Role>> all() {
        return Stream.empty();
    }

    @Override
    public <Expect extends User<R>, R extends Role> Expect byRole(Class<R> roleClass, Class<Expect> expectedSet) {
        return null;
    }

    @Override
    public <R extends Role> Users<R> byRole(Class<Role> roleClass) {
        return null;
    }

    @Override
    public Users<Role> participants(Course course) {
        return null;
    }

    @Override
    public Optional<User<Role>> fetch(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<User<Role>> fetch(String username) {
        return Optional.empty();
    }
}
