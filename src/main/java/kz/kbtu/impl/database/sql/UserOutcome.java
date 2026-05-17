package kz.kbtu.impl.database.sql;

import com.jcabi.jdbc.Outcome;
import kz.kbtu.api.Role;
import kz.kbtu.api.User;
import kz.kbtu.impl.EagerUserImpl;
import kz.kbtu.impl.UserBuilderImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class UserOutcome<T extends Role> implements Outcome.Mapping<User<T>> {
    protected final SQLBasedSchool database;

    protected UserOutcome(SQLBasedSchool database) {
        this.database = database;
    }

    abstract Role.RoleBuilder<T> mapRole(ResultSet rset) throws SQLException;

    @Override
    public User<T> map(ResultSet rset) throws SQLException {
        return database.userBuilder()
                .role(mapRole(rset))
                .build();
    }
}
