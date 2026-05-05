package kz.kbtu.impl.entity.database;

import at.favre.lib.crypto.bcrypt.BCrypt;
import kz.kbtu.entity.Database;
import kz.kbtu.entity.Role;
import kz.kbtu.entity.User;
import kz.kbtu.impl.entity.EagerUserBuilderImpl;

import java.util.UUID;

public class NonCommitableUserBuilderImpl<T extends Role> extends EagerUserBuilderImpl<T> {
    public NonCommitableUserBuilderImpl(BCrypt.Hasher hashing, Database database) {
        super(hashing, database);
    }

    public NonCommitableUserBuilderImpl(BCrypt.Hasher hashing, Database database, Role.RoleBuilder<T> role) {
        super(hashing, database, role);
    }

    @Override
    public <R extends Role> User.UserBuilder<R> role(Role.RoleBuilder<R> builder) {
        return new NonCommitableUserBuilderImpl<>(this.hashing, database, builder)
                .fullName(fullName)
                .username(name)
                .password(plainPassword);
    }

    @Override
    public User<T> build() {
        return new NonCommitableEagerUserImpl<>(
                UUID.randomUUID(),
                name,
                fullName,
                hashing.hashToString(12, plainPassword.toCharArray())
        );
    }
}
