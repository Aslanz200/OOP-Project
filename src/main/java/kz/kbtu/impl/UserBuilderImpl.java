package kz.kbtu.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import kz.kbtu.api.School;
import kz.kbtu.api.Role;
import kz.kbtu.api.User;

import java.util.ArrayList;
import java.util.List;

public abstract class UserBuilderImpl<T extends Role> implements User.UserBuilder<T> {
    protected final BCrypt.Hasher hashing;
    protected final School school;

    protected String plainPassword;
    protected List<String> fullName = List.of();
    protected Role.RoleBuilder<T> role;
    protected String name;

    public UserBuilderImpl(BCrypt.Hasher hashing, School school) {
        this.hashing = hashing;
        this.school = school;
    }
    public UserBuilderImpl(BCrypt.Hasher hashing, School school, Role.RoleBuilder<T> role) {
        this(hashing, school);
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
