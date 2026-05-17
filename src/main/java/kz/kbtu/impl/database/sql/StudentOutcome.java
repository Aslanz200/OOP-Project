package kz.kbtu.impl.database.sql;

import kz.kbtu.api.Role;
import kz.kbtu.api.role.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentOutcome extends UserOutcome<Student> {
    protected StudentOutcome(SQLBasedSchool database) {
        super(database);
    }

    @Override
    Role.RoleBuilder<Student> mapRole(ResultSet rset) throws SQLException {
        return database.roleBuilder(
                Student.class,
                Student.StudentBuilder.class
                )
                .get()
                .year(rset.getInt("year"));
    }
}
