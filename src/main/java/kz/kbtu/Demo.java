package kz.kbtu;

import kz.kbtu.exceptions.AuthException;
import kz.kbtu.exceptions.CreditLimitException;
import kz.kbtu.exceptions.LowHIndexException;
import kz.kbtu.exceptions.NotResearcherException;
import kz.kbtu.models.academic.Course;
import kz.kbtu.models.academic.Lesson;
import kz.kbtu.models.academic.LessonType;
import kz.kbtu.models.academic.Mark;
import kz.kbtu.models.research.ResearchPaper;
import kz.kbtu.models.research.ResearchProject;
import kz.kbtu.models.research.Researcher;
import kz.kbtu.models.research.ResearcherDecorator;
import kz.kbtu.models.users.Admin;
import kz.kbtu.models.users.Manager;
import kz.kbtu.models.users.ManagerType;
import kz.kbtu.models.users.Student;
import kz.kbtu.models.users.Teacher;
import kz.kbtu.models.users.TeacherTitle;
import kz.kbtu.models.users.User;
import kz.kbtu.patterns.Database;
import kz.kbtu.patterns.EventManager;
import kz.kbtu.patterns.Observer;
import kz.kbtu.patterns.UserFactory;
import kz.kbtu.services.AuthService;
import kz.kbtu.services.ReportService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Console demo of the KBTU University Information System.
 *
 * <p>Seeds an in-memory database, then offers an authenticated menu per role.
 * Showcases:
 * <ul>
 *     <li><b>Singleton</b> — {@link Database#getInstance()}, {@link EventManager#getInstance()}</li>
 *     <li><b>Factory</b> — {@link UserFactory#createUser}</li>
 *     <li><b>Observer</b> — logs printed live via console subscriber</li>
 *     <li><b>Strategy</b> — {@link Researcher#printPapers(Comparator)} with three comparators</li>
 *     <li><b>Decorator</b> — {@link ResearcherDecorator} wraps an Employee</li>
 * </ul>
 * </p>
 */
public class Demo {

    private static final Scanner SC = new Scanner(System.in);
    private static final AuthService AUTH = new AuthService();
    private static final ReportService REPORTS = new ReportService();
    private static final List<Researcher> RESEARCHERS = new ArrayList<>();

    public static void main(String[] args) {
        EventManager.getInstance().subscribe(new ConsoleLogger());
        seed();

        System.out.println("===========================================");
        System.out.println(" KBTU Research-Oriented University System  ");
        System.out.println("===========================================");

        while (true) {
            System.out.println("\n[1] Login   [2] List users   [3] Demo serialization   [0] Exit");
            System.out.print("> ");
            String choice = SC.nextLine().trim();
            switch (choice) {
                case "1": loginFlow(); break;
                case "2": Database.getInstance().getUsers().forEach(u -> System.out.println("  " + u)); break;
                case "3": serializationDemo(); break;
                case "0":
                    System.out.println("Bye!");
                    return;
                default: System.out.println("Unknown option");
            }
        }
    }

    // ---------- Authentication + role dispatch ----------

    private static void loginFlow() {
        System.out.print("username: ");
        String u = SC.nextLine().trim();
        System.out.print("password: ");
        String p = SC.nextLine().trim();
        try {
            User user = AUTH.login(u, p);
            System.out.println("Welcome, " + user.fullName() + " (" + user.getClass().getSimpleName() + ")");
            roleMenu(user);
            AUTH.logout(user);
        } catch (AuthException e) {
            System.out.println("AUTH ERROR: " + e.getMessage());
        }
    }

    private static void roleMenu(User user) {
        if (user instanceof Student)  studentMenu((Student) user);
        else if (user instanceof Teacher) teacherMenu((Teacher) user);
        else if (user instanceof Manager) managerMenu((Manager) user);
        else if (user instanceof Admin)   adminMenu((Admin) user);
    }

    // ---------- Student menu ----------

    private static void studentMenu(Student s) {
        while (true) {
            System.out.println("\n-- STUDENT MENU [" + s.fullName() + "] --");
            System.out.println("[1] View courses  [2] Register for course  [3] View grades  [4] Transcript");
            System.out.println("[5] Rate teacher  [6] Set supervisor (4th year)  [0] Logout");
            System.out.print("> ");
            switch (SC.nextLine().trim()) {
                case "1":
                    s.getCourses().forEach(c -> System.out.println("  " + c));
                    break;
                case "2": {
                    listCourses();
                    System.out.print("courseId: ");
                    Course c = findCourse(SC.nextLine().trim());
                    if (c == null) { System.out.println("not found"); break; }
                    try { s.registerForCourse(c); System.out.println("Registered."); }
                    catch (CreditLimitException e) { System.out.println("FAIL: " + e.getMessage()); }
                    break;
                }
                case "3": s.viewGrades().forEach(m -> System.out.println("  " + m)); break;
                case "4": s.viewTranscript(); break;
                case "5": {
                    listTeachers();
                    System.out.print("teacher username: ");
                    Teacher t = findTeacher(SC.nextLine().trim());
                    if (t == null) { System.out.println("not found"); break; }
                    System.out.print("rating (0..5): ");
                    s.rateTeacher(t, Double.parseDouble(SC.nextLine().trim()));
                    break;
                }
                case "6": {
                    if (s.getYear() != 4) { System.out.println("Supervisor required only for 4th year"); break; }
                    listResearchers();
                    System.out.print("researcher index: ");
                    int idx = Integer.parseInt(SC.nextLine().trim());
                    try { s.setSupervisor(RESEARCHERS.get(idx)); System.out.println("Supervisor set."); }
                    catch (LowHIndexException e) { System.out.println("FAIL: " + e.getMessage()); }
                    break;
                }
                case "0": return;
                default: System.out.println("Unknown option");
            }
        }
    }

    // ---------- Teacher menu ----------

    private static void teacherMenu(Teacher t) {
        while (true) {
            System.out.println("\n-- TEACHER MENU [" + t.fullName() + "] --");
            System.out.println("[1] View courses  [2] View students  [3] Put mark");
            System.out.println("[4] Send message  [5] My papers (if Researcher)  [0] Logout");
            System.out.print("> ");
            switch (SC.nextLine().trim()) {
                case "1": t.getCourses().forEach(c -> System.out.println("  " + c)); break;
                case "2": {
                    System.out.print("courseId: ");
                    Course c = findCourse(SC.nextLine().trim());
                    if (c == null) { System.out.println("not found"); break; }
                    t.viewStudents(c).forEach(st -> System.out.println("  " + st));
                    break;
                }
                case "3": {
                    System.out.print("student username: ");
                    Student st = (Student) Database.getInstance().findUser(SC.nextLine().trim()).orElse(null);
                    if (st == null) { System.out.println("not found"); break; }
                    System.out.print("courseId: ");
                    Course c = findCourse(SC.nextLine().trim());
                    if (c == null) { System.out.println("not found"); break; }
                    System.out.print("final exam score: ");
                    t.putMark(st, c, Double.parseDouble(SC.nextLine().trim()));
                    break;
                }
                case "4": {
                    System.out.print("recipient username: ");
                    User to = Database.getInstance().findUser(SC.nextLine().trim()).orElse(null);
                    if (!(to instanceof kz.kbtu.models.users.Employee)) {
                        System.out.println("recipient must be employee"); break;
                    }
                    System.out.print("message: ");
                    t.sendMessage((kz.kbtu.models.users.Employee) to, SC.nextLine());
                    break;
                }
                case "5": researcherForUser(t); break;
                case "0": return;
                default: System.out.println("Unknown option");
            }
        }
    }

    // ---------- Manager menu ----------

    private static void managerMenu(Manager m) {
        while (true) {
            System.out.println("\n-- MANAGER MENU [" + m.fullName() + "] --");
            System.out.println("[1] Approve registration  [2] Assign teacher  [3] Add news");
            System.out.println("[4] Grade report          [5] Course report   [6] Students sorted by GPA  [0] Logout");
            System.out.print("> ");
            switch (SC.nextLine().trim()) {
                case "1": {
                    System.out.print("student username: ");
                    Student st = (Student) Database.getInstance().findUser(SC.nextLine().trim()).orElse(null);
                    System.out.print("courseId: ");
                    Course c = findCourse(SC.nextLine().trim());
                    if (st != null && c != null) m.approveRegistration(st, c);
                    break;
                }
                case "2": {
                    System.out.print("teacher username: ");
                    Teacher t = findTeacher(SC.nextLine().trim());
                    System.out.print("courseId: ");
                    Course c = findCourse(SC.nextLine().trim());
                    if (t != null && c != null) m.assignTeacher(t, c);
                    break;
                }
                case "3":
                    System.out.print("headline: ");
                    m.manageNews(SC.nextLine());
                    break;
                case "4": System.out.println(REPORTS.gradeStatistics()); break;
                case "5":
                    System.out.print("courseId: ");
                    Course c = findCourse(SC.nextLine().trim());
                    if (c != null) System.out.println(REPORTS.courseStatistics(c));
                    break;
                case "6":
                    Database.getInstance().getUsers().stream()
                            .filter(u -> u instanceof Student).map(u -> (Student) u)
                            .sorted(Comparator.comparingDouble(Student::getGpa).reversed())
                            .forEach(s -> System.out.println("  " + s));
                    break;
                case "0": return;
                default: System.out.println("Unknown option");
            }
        }
    }

    // ---------- Admin menu ----------

    private static void adminMenu(Admin a) {
        while (true) {
            System.out.println("\n-- ADMIN MENU [" + a.fullName() + "] --");
            System.out.println("[1] Add user (via Factory)  [2] Remove user  [3] List users  [4] View logs  [0] Logout");
            System.out.print("> ");
            switch (SC.nextLine().trim()) {
                case "1": {
                    System.out.print("type (student/teacher/manager/admin): ");
                    String type = SC.nextLine().trim();
                    System.out.print("username: ");        String u = SC.nextLine().trim();
                    System.out.print("password: ");        String p = SC.nextLine().trim();
                    System.out.print("first name: ");      String fn = SC.nextLine().trim();
                    System.out.print("last name: ");       String ln = SC.nextLine().trim();
                    String id = "U" + (Database.getInstance().getUsers().size() + 1);
                    try {
                        User user;
                        switch (type) {
                            case "student":
                                System.out.print("year: ");
                                int year = Integer.parseInt(SC.nextLine().trim());
                                user = UserFactory.createUser("student", u, p, fn, ln, id, year, 0.0);
                                break;
                            case "teacher":
                                user = UserFactory.createUser("teacher", u, p, fn, ln, id,
                                        "E" + id, "CS", TeacherTitle.TUTOR);
                                break;
                            case "manager":
                                user = UserFactory.createUser("manager", u, p, fn, ln, id,
                                        "E" + id, "CS", ManagerType.DEPARTMENT);
                                break;
                            case "admin":
                                user = UserFactory.createUser("admin", u, p, fn, ln, id, "E" + id, "ADM");
                                break;
                            default:
                                System.out.println("unknown type"); continue;
                        }
                        a.addUser(user);
                    } catch (Exception e) {
                        System.out.println("FAIL: " + e.getMessage());
                    }
                    break;
                }
                case "2": {
                    System.out.print("username: ");
                    User u = Database.getInstance().findUser(SC.nextLine().trim()).orElse(null);
                    if (u != null) a.removeUser(u); else System.out.println("not found");
                    break;
                }
                case "3":
                    Database.getInstance().getUsers().forEach(u -> System.out.println("  " + u));
                    break;
                case "4":
                    a.viewLogs().forEach(l -> System.out.println("  LOG: " + l));
                    break;
                case "0": return;
                default: System.out.println("Unknown option");
            }
        }
    }

    // ---------- Researcher sub-menu (used by Teacher menu) ----------

    private static void researcherForUser(User user) {
        Researcher r = RESEARCHERS.stream()
                .filter(x -> x instanceof ResearcherDecorator
                        && ((ResearcherDecorator) x).getEmployee().equals(user))
                .findFirst().orElse(null);
        if (r == null) { System.out.println("You are not registered as a Researcher"); return; }
        while (true) {
            System.out.println("\n-- RESEARCHER MENU [" + r.identity() + "] h=" + r.getHIndex() + " --");
            System.out.println("[1] My papers (by citations)  [2] My papers (by date)  [3] My papers (by pages)");
            System.out.println("[4] All papers (by citations) [5] Top cited researcher  [6] Top cited of 2025  [0] Back");
            System.out.print("> ");
            switch (SC.nextLine().trim()) {
                case "1": r.printPapers(ResearchPaper.BY_CITATIONS); break;
                case "2": r.printPapers(ResearchPaper.BY_DATE); break;
                case "3": r.printPapers(ResearchPaper.BY_PAGES); break;
                case "4": REPORTS.printAllPapers(ResearchPaper.BY_CITATIONS); break;
                case "5": REPORTS.topCitedResearcher(RESEARCHERS)
                            .ifPresent(x -> System.out.println("TOP: " + x.identity())); break;
                case "6": REPORTS.topCitedOfYear(RESEARCHERS, 2025)
                            .ifPresent(x -> System.out.println("TOP 2025: " + x.identity())); break;
                case "0": return;
                default: System.out.println("Unknown option");
            }
        }
    }

    // ---------- Helpers ----------

    private static void listCourses() {
        Database.getInstance().getCourses().forEach(c -> System.out.println("  " + c));
    }
    private static void listTeachers() {
        Database.getInstance().getUsers().stream()
                .filter(u -> u instanceof Teacher)
                .forEach(u -> System.out.println("  " + u));
    }
    private static void listResearchers() {
        for (int i = 0; i < RESEARCHERS.size(); i++) {
            System.out.println("  [" + i + "] " + RESEARCHERS.get(i));
        }
    }
    private static Course findCourse(String id) {
        return Database.getInstance().getCourses().stream()
                .filter(c -> c.getCourseId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
    private static Teacher findTeacher(String username) {
        User u = Database.getInstance().findUser(username).orElse(null);
        return (u instanceof Teacher) ? (Teacher) u : null;
    }

    private static void serializationDemo() {
        try {
            String path = "db.ser";
            Database.getInstance().save(path);
            System.out.println("Saved to " + path);
            Database loaded = Database.load(path);
            System.out.println("Loaded back: " + loaded);
        } catch (Exception e) {
            System.out.println("FAIL: " + e.getMessage());
        }
    }

    // ---------- Console observer ----------

    static class ConsoleLogger implements Observer {
        @Override
        public void update(String message) {
            System.out.println("[event] " + message);
        }
    }

    // ---------- Seed data ----------

    private static void seed() {
        Database db = Database.getInstance();

        Course oop = new Course("CSCI152", "Object-Oriented Programming", 6);
        Course alg = new Course("CSCI235", "Algorithms", 6);
        Course db2 = new Course("CSCI275", "Databases", 5);
        db.getCourses().addAll(Arrays.asList(oop, alg, db2));

        oop.getLessons().add(new Lesson(LessonType.LECTURE, oop));
        oop.getLessons().add(new Lesson(LessonType.PRACTICE, oop));
        alg.getLessons().add(new Lesson(LessonType.LECTURE, alg));

        Admin admin = (Admin) UserFactory.createUser("admin",
                "admin", "admin", "Yerlan", "Beibitov", "U1", "E1", "ADM");
        Manager dean = (Manager) UserFactory.createUser("manager",
                "dean", "dean", "Anna", "Smirnova", "U2", "E2", "CS", ManagerType.DEAN);
        Teacher prof = (Teacher) UserFactory.createUser("teacher",
                "prof", "prof", "Bek", "Aitbayev", "U3", "E3", "CS", TeacherTitle.PROFESSOR);
        Teacher tutor = (Teacher) UserFactory.createUser("teacher",
                "tutor", "tutor", "Madi", "Nurlanov", "U4", "E4", "CS", TeacherTitle.TUTOR);
        Student s2 = (Student) UserFactory.createUser("student",
                "bob", "bob", "Bob", "Marley", "U6", 2, 3.1);
        Student s3 = (Student) UserFactory.createUser("student",
                "amir", "amir", "Amir", "Yerlan", "U7", 4, 3.4);

        db.getUsers().addAll(Arrays.asList(admin, dean, prof, tutor, s2, s3));

        prof.manageCourse(oop);
        prof.manageCourse(alg);
        tutor.manageCourse(db2);
        // more than 1 instructor per course (spec):
        tutor.manageCourse(oop);

        try {
            s2.registerForCourse(oop);
            s3.registerForCourse(db2);
            s3.registerForCourse(alg);
        } catch (CreditLimitException e) { System.out.println(e.getMessage()); }

        db.getMarks().add(new Mark(s2, oop, 55, 60, 45));
        db.getMarks().add(new Mark(s3, db2, 70, 72, 68));
        db.getMarks().add(new Mark(s3, alg, 82, 78, 85));

        // ---------- Researchers via Decorator pattern ----------
        ResearcherDecorator profResearcher = new ResearcherDecorator(prof, 7);   // Professor — always Researcher
        ResearcherDecorator tutorResearcher = new ResearcherDecorator(tutor, 2); // non-professor teacher can also be Researcher
        ResearcherDecorator adminResearcher = new ResearcherDecorator(admin, 4); // employee who is neither Teacher nor Student
        RESEARCHERS.add(profResearcher);
        RESEARCHERS.add(tutorResearcher);
        RESEARCHERS.add(adminResearcher);

        ResearchPaper p1 = new ResearchPaper("Compositional ML for Education",
                Arrays.asList("Aitbayev B.", "Smirnova A."), "IEEE Access", 12,
                date("2025-03-12"), 145, "10.1109/EXAMPLE.2025.0001");
        ResearchPaper p2 = new ResearchPaper("Graph Algorithms Revisited",
                Arrays.asList("Aitbayev B."), "ACM TOPLAS", 24,
                date("2024-11-05"), 67, "10.1109/EXAMPLE.2024.0002");
        ResearchPaper p3 = new ResearchPaper("Tutoring at Scale",
                Arrays.asList("Nurlanov M."), "EduTech", 8,
                date("2025-01-20"), 22, "10.1109/EXAMPLE.2025.0003");
        ResearchPaper p4 = new ResearchPaper("Admin Workflows in Universities",
                Arrays.asList("Kozhamuratova A."), "Higher Ed Today", 16,
                date("2023-06-15"), 51, "10.1109/EXAMPLE.2023.0004");

        profResearcher.addPaper(p1);
        profResearcher.addPaper(p2);
        tutorResearcher.addPaper(p3);
        adminResearcher.addPaper(p4);

        db.getPapers().addAll(Arrays.asList(p1, p2, p3, p4));

        ResearchProject project = new ResearchProject("AI in Education");
        try {
            project.addParticipant(profResearcher);
            project.addParticipant(tutorResearcher);
        } catch (NotResearcherException e) { System.out.println(e.getMessage()); }
        project.addPaper(p1);
        project.addPaper(p3);
        db.getProjects().add(project);

        // 4th year student demo: trying h<3 throws; with h>=3 succeeds
        try {
            s3.setSupervisor(tutorResearcher); // h=2 → throws
        } catch (LowHIndexException e) {
            System.out.println("[seed] expected exception caught: " + e.getMessage());
        }
        try {
            s3.setSupervisor(profResearcher); // h=7 → ok
        } catch (LowHIndexException e) { System.out.println(e.getMessage()); }

        // Manager seeds a news headline
        dean.manageNews("Welcome to the new academic year!");
    }

    private static java.util.Date date(String iso) {
        try { return new SimpleDateFormat("yyyy-MM-dd").parse(iso); }
        catch (Exception e) { return null; }
    }
}
