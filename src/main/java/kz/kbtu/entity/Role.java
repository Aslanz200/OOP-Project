package kz.kbtu.entity;

import java.util.List;

public interface Role {

    List<String> permissions();
    boolean can(String permission);

    Class<?> implementation();

}
