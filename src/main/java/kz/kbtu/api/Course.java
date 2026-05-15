package kz.kbtu.api;

import kz.kbtu.api.sets.Users;

import java.util.UUID;

public interface Course {
    UUID id();
    String codeName();
    String name();
}
