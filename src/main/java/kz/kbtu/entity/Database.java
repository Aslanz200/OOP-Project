package kz.kbtu.entity;

import kz.kbtu.entity.collection.Users;
import kz.kbtu.entity.role.Student;

import java.util.Iterator;
import java.util.stream.Stream;

public interface Database {
    Users users();
    User.UserBuilder<?> userBuilder();
    <T extends Role.RoleBuilder<? extends Role>> T roleBuilder(Class<? extends Role> forRole);

    <R extends Role> User<R> updateRole(User<?> user, Role.RoleBuilder<R> builder);
}
