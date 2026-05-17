package kz.kbtu.impl.database.sql.api.role;

import kz.kbtu.api.role.Student;
import kz.kbtu.impl.EagerUserImpl;
import kz.kbtu.impl.database.sql.SQLBasedSchool;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class StudentUserImpl extends EagerUserImpl<Student> {

    private final SQLBasedSchool database;

    public StudentUserImpl(
            UUID id, String username, List<String> fullName, String hashedPassword, SQLBasedSchool database) {
        super(id, username, fullName, hashedPassword);
        this.database = database;
    }

    @Override
    public Optional<Student> role() {

        return super.role();
    }
}


