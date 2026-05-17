package kz.kbtu.impl.database.sql;

import kz.kbtu.api.Role;
import kz.kbtu.api.role.Teacher;
import kz.kbtu.api.role.TeacherTitle;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherOutcome extends UserOutcome<Teacher> {
    protected TeacherOutcome(SQLBasedSchool database) {
        super(database);
    }

    @Override
    Role.RoleBuilder<Teacher> mapRole(ResultSet rset) throws SQLException {
        return database.roleBuilder(
                Teacher.class,
                Teacher.TeacherBuilder.class
        )
                .get()
                .title(TeacherTitle.valueOf(rset.getString("title")));
    }
}
