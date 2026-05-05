package kz.kbtu.entity;

import kz.kbtu.entity.collection.Users;

import java.util.UUID;

public interface Course {
    UUID id();
    String codeName();
    String name();

    Users participants();
}
