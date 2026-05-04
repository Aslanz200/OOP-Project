package kz.kbtu.entity;

import java.util.Optional;
import java.util.UUID;

public interface User<T extends Role> {
    UUID id();
    String username();

    String firstName();
    String lastName();

    Optional<T> role();

    boolean checkPassword(String password);

}
