package kz.kbtu.impl.database.sql;

import com.jcabi.jdbc.JdbcSession;
import kz.kbtu.api.School;
import kz.kbtu.api.Role;
import kz.kbtu.api.User;
import kz.kbtu.api.sets.Users;
import kz.kbtu.impl.database.sql.api.sets.AllUsers;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

public record SQLBasedSchool(DataSource datasource) implements School {

    public void initTables() throws SQLException {
        new JdbcSession(datasource)
                .sql(
                        """
                                    CREATE TABLE IF NOT EXISTS `users` (
                                        `id` BLOB PRIMARY KEY UNIQUE,
                                        `username` VARCHAR(256),
                                        `fullName` VARCHAR(512),
                                        `password` BLOB);
                                    CREATE TABLE IF NOT EXISTS `students` (
                                        `user` BLOB,
                                        `year` INTEGER,
                                        FOREIGN KEY (`user`) REFERENCES `users`(`id`)
                                    );
                                    CREATE TABLE IF NOT EXISTS `teachers` (
                                        `user` BLOB,
                                        `title` VARCHAR(128),
                                        FOREIGN KEY (`user`) REFERENCES `users`(`id`)
                                    )
                                    CREATE TABLE IF NOT EXISTS `marks` (
                                        `course` BLOB,
                                        `semester` BLOB,
                                        `user` BLOB,
                                        `first_half` DECIMAL,
                                        `second_half` DECIMAL,
                                        `final` DECIMAL,
                                        FOREIGN KEY (`user`) REFERENCES `users`(`id`)
                                    );
                                    CREATE TABLE IF NOT EXISTS `lesson_participants` (
                                        `user` BLOB,
                                        `lesson` BLOB,
                                        FOREIGN KEY (`user`) REFERENCES `users`(`id`)
                                    );
                                    CREATE TABLE IF NOT EXISTS `course_participants` (
                                        `user` BLOB,
                                        `course` BLOB,
                                        FOREIGN KEY (`user`) REFERENCES `users`(`id`)
                                    );
                                """
                )
                .execute()
                .commit();
    }

    @Override
    public Users<?> users() {
        return new AllUsers(this);
    }

    @Override
    public User.UserBuilder<?> userBuilder() {
        return null;
    }

    @Override
    public User.UserBuilder<?> userBuilder(boolean commit) {
        return null;
    }

    @Override
    public <Expect extends Role.RoleBuilder<R>, R extends Role> Optional<Expect> roleBuilder(Class<R> roleClass) {
        return Optional.empty();
    }

    @Override
    public <Expect extends Role.RoleBuilder<R>, R extends Role> Optional<Expect> roleBuilder(Class<R> roleClass, Class<Expect> expectedBuilder) {
        return Optional.empty();
    }

    @Override
    public <R extends Role> User<R> updateRole(User<?> user, Role.RoleBuilder<R> builder) {
        return null;
    }
}
