package kz.kbtu.impl.database.sql;

import com.jcabi.jdbc.JdbcSession;
import kz.kbtu.api.Database;
import kz.kbtu.api.Role;
import kz.kbtu.api.User;
import kz.kbtu.api.sets.Users;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class SQLDatabase implements Database {

    private final DataSource datasource;

    public SQLDatabase(DataSource connection) {
        this.datasource = connection;
    }

    public void initTables() throws SQLException {
        new JdbcSession(datasource)
                .sql(
"""
    CREATE TABLE IF NOT EXISTS `users` (
        `id` BLOB PRIMARY KEY UNIQUE,
        `username` VARCHAR(256),
        fullName` VARCHAR(512),
        `password` BLOB);
    CREATE TABLE IF NOT EXISTS `students` (
        `user` BLOB,
        `balance` INTEGER,
        FOREIGN KEY (`user`) REFERENCES `users`(`id`)
    );
    CREATE TABLE IF NOT EXISTS `courses` (
        `id` BLOB PRIMARY KEY,
        `codename` VARCHAR(32),
        `name` VARCHAR(128),
        `credits` INTEGER
        );
    CREATE TABLE IF NOT EXISTS `course_participants`(
        `course` BLOB,
        `user` BLOB,
        FOREIGN KEY (`course`) REFERENCES `courses`(`id`),
        FOREIGN KEY (`user`) REFERENCES `users`(`id`)
    );
    CREATE TABLE IF NOT EXISTS `lessons`(
        `course` BLOB,
        `date_object` BLOB PRIMARY KEY,
        FOREIGN KEY (`course`) REFERENCES `courses`
    );
    CREATE TABLE IF NOT EXISTS `lesson_participants`(
        `user` BLOB,
        `lesson` BLOB,
        FOREIGN KEY (`user`) REFERENCES `users`(`id`),
        FOREIGN KEY (`lesson`) REFERENCES `lessons`
    );
    CREATE TABLE IF NOT EXISTS `marks` (
        `course` BLOB,
        `user` BLOB,
        `first_half` DECIMAL,
        `second_half` DECIMAL,
        `final` DECIMAL,
        FOREIGN KEY (`course`) REFERENCES `courses`(`id`),
        FOREIGN KEY (`user`) REFERENCES `users`(`id`)
    );
"""
                )
                .execute()
                .commit();
    }

    @Override
    public Users<?> users() {
        return null;
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
