package kz.kbtu;

import kz.kbtu.entity.Database;
import kz.kbtu.entity.User;
import kz.kbtu.entity.role.Student;
import kz.kbtu.impl.entity.database.DeclarativeDatabase;

public class Main {
    static void main() {
        Database database = new DeclarativeDatabase();

        try(User<Student> user = database
                .userBuilder()
                .username("Hi")
                .password("Sigma67")
                .fullName("Hi", "Hi", "Hi")
                .role(
                        database
                                .<Student.StudentBuilder>roleBuilder(Student.class)
                                .year(2)
                                .gpa(6.7f)
                                .fails(0)
                )
                .build()) {

            IO.println(user.checkPassword("Sigma67"));
            user.role().ifPresent(IO::println);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
