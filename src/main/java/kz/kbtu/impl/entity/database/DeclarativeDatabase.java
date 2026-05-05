package kz.kbtu.impl.entity.database;

import at.favre.lib.crypto.bcrypt.BCrypt;
import kz.kbtu.entity.Database;
import kz.kbtu.entity.Role;
import kz.kbtu.entity.User;
import kz.kbtu.entity.collection.Users;
import kz.kbtu.entity.role.Student;
import kz.kbtu.impl.entity.EagerUserBuilderImpl;
import kz.kbtu.impl.entity.role.StudentBuilderImpl;

public class DeclarativeDatabase implements Database {
    @Override
    public Users users() {
        return Users.ofEmpty();
    }


    @Override
    public User.UserBuilder<?> userBuilder() {
        return new NonCommitableUserBuilderImpl<>(BCrypt.withDefaults(), this);
    }

    @Override
    public <T extends Role.RoleBuilder<? extends Role>> T roleBuilder(Class<? extends Role> forRole) {
        if (forRole.isAssignableFrom(Student.class)) {
            return (T) (new StudentBuilderImpl(this));
        }
        return null;
    }


    @Override
    public <R extends Role> User<R> updateRole(User<?> user, Role.RoleBuilder<R> builder) {
        throw new RuntimeException("Not implemented");
    }
}
