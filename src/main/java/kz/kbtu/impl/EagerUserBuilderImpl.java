package kz.kbtu.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import kz.kbtu.api.Database;
import kz.kbtu.api.Role;
import kz.kbtu.api.User;

import java.util.ArrayList;
import java.util.List;

public abstract class EagerUserBuilderImpl<T extends Role> implements User.UserBuilder<T> {
    protected final BCrypt.Hasher hashing;
    protected final Database database;

    protected String plainPassword;
    protected List<String> fullName = List.of();
    protected Role.RoleBuilder<T> role;
    protected String name;

    public EagerUserBuilderImpl(BCrypt.Hasher hashing, Database database) {
        this.hashing = hashing;
        this.database = database;
    }
    public EagerUserBuilderImpl(BCrypt.Hasher hashing, Database database, Role.RoleBuilder<T> role) {
        this(hashing, database);
        this.role = role;
    }


    @Override
    public User.UserBuilder<T> username(String name) {
        this.name = name;
        return this;
    }

    @Override
    public User.UserBuilder<T> password(String password) {
        this.plainPassword = password;
        return this;
    }



    @Override
    public User.UserBuilder<T> fullName(String... names) {
        this.fullName = List.of(names);
        return this;
    }

    @Override
    public User.UserBuilder<T> fullName(Iterable<String> names) {
        ArrayList<String> a = new ArrayList<>();
        for (String s : names) {
            a.add(s);
        }
        this.fullName = a;
        return this;
    }
}
