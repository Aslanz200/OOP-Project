package kz.kbtu.impl.entity.database;

import kz.kbtu.entity.Role;
import kz.kbtu.impl.entity.EagerUserImpl;

import java.util.UUID;

public class NonCommitableEagerUserImpl<T extends Role> extends EagerUserImpl<T> {
    public NonCommitableEagerUserImpl(UUID id, String username, Iterable<String> fullName, String hashedPassword) {
        super(id, username, fullName, hashedPassword);
    }

    @Override
    public void close() throws Exception {
        IO.println("Closed");
    }
}
