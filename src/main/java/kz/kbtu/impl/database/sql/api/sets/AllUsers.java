package kz.kbtu.impl.database.sql.api.sets;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import com.jcabi.jdbc.Outcome;
import kz.kbtu.api.Course;
import kz.kbtu.api.Role;
import kz.kbtu.api.User;
import kz.kbtu.api.sets.Users;
import kz.kbtu.impl.database.sql.SQLBasedSchool;
import kz.kbtu.impl.database.sql.UserOutcome;
import kz.kbtu.impl.database.sql.Util;
import org.sqlite.JDBC;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class AllUsers implements Users<Role> {

    private final SQLBasedSchool database;

    public AllUsers(SQLBasedSchool database) {
        this.database = database;
    }

    @Override
    public Stream<User<Role>> all() {
        try {
            return new JdbcSession(database.datasource())
                    .sql("SELECT * FROM users")
                    .select(new ListOutcome<>(new UserOutcome())).stream();
        } catch (SQLException e) {
            return Stream.empty();
        }
    }

    @Override
    public <Expect extends User<R>, R extends Role> Expect byRole(Class<R> roleClass, Class<Expect> expectedSet) {
        return null;
    }

    @Override
    public <R extends Role> Users<R> byRole(Class<Role> roleClass) {
        return null;
    }

    @Override
    public Users<Role> participants(Course course) {
        try {
            return new JdbcSession(database.datasource())
                    .sql("SELECT * FROM course_participants FULL JOIN users ON course_participants.user = ?")
                    .set(Util.serialize(course.id()))
                    .select(new ListOutcome<>(new UserOutcome()))
                    .stream()
        } catch (SQLException e) {
            return Users.ofEmpty();
        }
    }

    @Override
    public Optional<User<Role>> fetch(UUID id) {
        try {
            return new JdbcSession(database.datasource())
                    .sql("SELECT * FROM users WHERE id = ?")
                    .set(Util.serialize(id))
                    .select(new ListOutcome<>(new UserOutcome()))
                    .stream()
                    .findFirst();
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User<Role>> fetch(String username) {
        try {
            return new JdbcSession(database.datasource())
                    .sql("SELECT * FROM users WHERE username = ?")
                    .set(username)
                    .select(new ListOutcome<>(new UserOutcome()))
                    .stream()
                    .findAny();
        } catch (SQLException e) {
            return Optional.empty();
        }
    }
}
