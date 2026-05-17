package kz.kbtu.api;

import kz.kbtu.api.sets.Users;

import java.util.Optional;

public interface School {


    Users<?> users();

    User.UserBuilder<?> userBuilder();
    User.UserBuilder<?> userBuilder(boolean commit);

    <Expect extends Role.RoleBuilder<R>, R extends Role> Optional<Expect> roleBuilder(
            Class<R> roleClass
    );

    <Expect extends Role.RoleBuilder<R>, R extends Role> Optional<Expect> roleBuilder(
            Class<R> roleClass,
            Class<Expect> expectedBuilder
    );

    <R extends Role> User<R> updateRole(User<?> user, Role.RoleBuilder<R> builder);

}
