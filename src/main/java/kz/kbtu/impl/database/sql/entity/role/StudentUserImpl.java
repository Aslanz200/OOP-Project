package kz.kbtu.impl.database.sql.entity.role;

import kz.kbtu.api.Role;
import kz.kbtu.api.User;
import kz.kbtu.api.role.Student;
import kz.kbtu.impl.EagerUserImpl;
import kz.kbtu.impl.database.sql.SQLDatabase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class StudentUserImpl extends EagerUserImpl<Student> {
    public StudentUserImpl(
            SQLDatabase database,
            UUID id, String username, List<String> fullName, String hashedPassword) {
        super(id, username, fullName, hashedPassword);
    }

    @Override
    public Optional<Student> role() {

        return super.role();
    }
}


