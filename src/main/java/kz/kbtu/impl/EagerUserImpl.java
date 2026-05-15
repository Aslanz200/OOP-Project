package kz.kbtu.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import kz.kbtu.api.Role;
import kz.kbtu.api.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public abstract class EagerUserImpl<T extends Role> implements User<T> {
    protected final UUID id;
    protected final String username;
    protected final List<String> fullName;

    protected T role;
    private final String hashedPassword;

    public EagerUserImpl(UUID id, String username, List<String> fullName, String hashedPassword) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.hashedPassword = hashedPassword;
    }

    @Override
    public UUID id() {
        return id;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public Stream<String> fullName() {
        return fullName.stream();
    }

    @Override
    public Optional<T> role() {
        return role == null ? Optional.empty() : Optional.of(role);
    }

    @Override
    public boolean is(Class<? extends Role> role) {
        if (this.role == null) return false;
        return role.isAssignableFrom(this.role.implementation());
    }

    @Override
    public boolean checkPassword(String password) {
        BCrypt.Result result = BCrypt
                .verifyer()
                .verify(password.toCharArray(), hashedPassword);
        return result.verified;
    }
}
