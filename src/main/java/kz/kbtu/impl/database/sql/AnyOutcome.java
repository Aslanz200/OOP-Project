package kz.kbtu.impl.database.sql;

import kz.kbtu.api.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnyOutcome extends UserOutcome<Role> {
    public AnyOutcome(SQLBasedSchool database) {
        super(database);
    }

    @Override
    Role.RoleBuilder<Role> mapRole(ResultSet rset) throws SQLException {
        return null;
    }
}
