package kz.kbtu.impl.entity;

import at.favre.lib.crypto.bcrypt.BCrypt;
import kz.kbtu.entity.Role;
import kz.kbtu.entity.User;

import java.util.Optional;
import java.util.UUID;

public abstract class EagerUserImpl<T extends Role> implements User<T> {
    protected final UUID id;
    protected final String username;
    protected final Iterable<String> fullName;

    protected T role;
    private final String hashedPassword;

    public EagerUserImpl(UUID id, String username, Iterable<String> fullName, String hashedPassword) {
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
    public Iterable<String> fullName() {
        return fullName;
    }

    @Override
    public Optional<T> role() {
        return role == null ? Optional.empty() : Optional.of(role);
    }

    @Override
    public boolean checkPassword(String password) {
        BCrypt.Result result = BCrypt
                .verifyer()
                .verify(password.toCharArray(), hashedPassword);
        return result.verified;
    }
}
