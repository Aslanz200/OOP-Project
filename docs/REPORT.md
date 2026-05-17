# Final Project Report
## KBTU Research-Oriented University Information System

**Course:** Object-Oriented Programming
**Team:** [list team members here — surnames and names]
**Team leader:** Liana Kozhamuratova
**Date:** May 17, 2026

---

## 1. Introduction

This project implements a console-based information system for a research-oriented
university. It models all primary actors (students, teachers, managers, admins,
researchers) and supports authentication, course registration, mark management,
research-paper tracking, and academic reporting.

The codebase follows OOP principles, applies five design patterns, uses Java
collections, comparators, custom exceptions, and persistent serialization.

---

## 2. Project structure

```
src/main/java/kz/kbtu/
├── Demo.java                          ← console demo with role menus
├── models/
│   ├── users/
│   │   ├── User.java (abstract)
│   │   ├── Employee.java (abstract)
│   │   ├── Teacher.java
│   │   ├── Student.java
│   │   ├── Manager.java
│   │   ├── Admin.java
│   │   ├── TeacherTitle.java (enum)
│   │   └── ManagerType.java (enum)
│   ├── academic/
│   │   ├── Course.java
│   │   ├── Lesson.java
│   │   ├── Mark.java
│   │   └── LessonType.java (enum)
│   └── research/
│       ├── Researcher.java (interface)
│       ├── ResearchPaper.java (Comparable + 3 Comparators)
│       ├── ResearchProject.java
│       └── ResearcherDecorator.java
├── patterns/
│   ├── Database.java       ← Singleton
│   ├── UserFactory.java    ← Factory
│   ├── EventManager.java   ← Observer subject
│   └── Observer.java       ← Observer interface
├── exceptions/
│   ├── LowHIndexException.java
│   ├── NotResearcherException.java
│   ├── AuthException.java
│   └── CreditLimitException.java
└── services/
    ├── AuthService.java
    └── ReportService.java
```

---

## 3. Class hierarchy

### Users
```
User (abstract, Serializable)
├── Employee (abstract)
│   ├── Teacher
│   ├── Manager
│   └── Admin
└── Student
```

### Research
```
Researcher (interface, Serializable)
└── ResearcherDecorator (wraps any Employee)
```

### Required classes — all present
User, Employee, Teacher, Manager, Student, Admin, Course, Mark, Lesson,
Researcher, ResearchPaper, ResearchProject ✓

---

## 4. Design patterns (5 used)

### 4.1 Singleton — `Database`, `EventManager`
Guarantees a single instance of the in-memory store and the event bus across the application.

```java
public class Database implements Serializable {
    private static Database instance;
    private Database() {}
    public static synchronized Database getInstance() {
        if (instance == null) instance = new Database();
        return instance;
    }
}
```

### 4.2 Factory — `UserFactory.createUser(type, …)`
Centralises construction of concrete `User` subclasses.

```java
User u = UserFactory.createUser("teacher", "prof", "prof",
        "Bek", "Aitbayev", "U3", "E3", "CS", TeacherTitle.PROFESSOR);
```

### 4.3 Observer — `EventManager` + `Observer`
Every user action publishes an event; `ConsoleLogger` (a subscriber) prints it live.
Admin's "View logs" simply reads `EventManager.getLog()`.

```java
public class EventManager {
    private final List<Observer> observers = new ArrayList<>();
    public void subscribe(Observer o) { observers.add(o); }
    public void notify(String message) {
        log.add(message);
        for (Observer o : observers) o.update(message);
    }
}
```

### 4.4 Strategy — `Researcher.printPapers(Comparator)`
Sorting algorithm is injected from outside. Three named comparators are provided
on `ResearchPaper`: `BY_DATE`, `BY_CITATIONS`, `BY_PAGES`.

```java
profResearcher.printPapers(ResearchPaper.BY_CITATIONS);
profResearcher.printPapers(ResearchPaper.BY_DATE);
profResearcher.printPapers(ResearchPaper.BY_PAGES);
```

### 4.5 Decorator — `ResearcherDecorator extends ... implements Researcher`
Wraps any `Employee` to add research abilities without changing the original class.
This resolves the spec's "Researcher mystery" — an employee who is neither teacher
nor student can still be a researcher.

```java
ResearcherDecorator adminResearcher = new ResearcherDecorator(admin, 4);
adminResearcher.addPaper(p4);
```

---

## 5. Required functionality — implementation map

| Requirement                                      | Where implemented                                    |
|--------------------------------------------------|------------------------------------------------------|
| Lesson type lecture/practice                     | `LessonType` enum                                    |
| Student is bachelor only                         | `Student.year` ∈ {1..4}                              |
| 4th-year student has supervisor                  | `Student.setSupervisor`                              |
| Supervisor h-index < 3 → custom exception        | `LowHIndexException` thrown in `setSupervisor`       |
| More than 1 instructor per course                | `Course.teachers` is a `List`, seed adds 2 to OOP   |
| Teachers/Students CAN be researchers             | `ResearcherDecorator` wraps any `Employee`           |
| Professor IS always a researcher                 | Seeded: `prof` (PROFESSOR) is wrapped as researcher |
| Employee who is neither teacher nor student      | Seeded: `admin` is also a researcher                 |
| `Researcher.printPapers(Comparator)`             | `Researcher` interface + 3 comparators               |
| Print papers across university sorted            | `ReportService.printAllPapers(Comparator)`           |
| Top-cited researcher                             | `ReportService.topCitedResearcher`                   |
| Top-cited researcher of the year                 | `ReportService.topCitedOfYear`                       |
| Non-researcher cannot join project → exception   | `NotResearcherException` in `ResearchProject.addParticipant` |
| Report generation (mark statistics)              | `ReportService.gradeStatistics()`                    |
| 4+ design patterns                               | 5 used (Singleton/Factory/Observer/Strategy/Decorator) |
| Comparable/Comparators                           | `ResearchPaper implements Comparable` + 3 Comparators |
| equals / hashCode / toString                     | `User`, `Course`, `ResearchPaper`                    |
| Serialization                                    | All models implement `Serializable`; `Database.save/load` |
| Authentication                                   | `AuthService.login` required before menu             |
| Enumerations                                     | `TeacherTitle`, `ManagerType`, `LessonType`          |
| Students ≤ 21 credits                            | `Student.MAX_CREDITS`, enforced in `registerForCourse` |
| Students ≤ 3 fails                               | `Student.MAX_FAILS`, enforced in `registerForCourse` |
| Mark = att1 + att2 + final                       | `Mark` class with weighted total                    |
| Demo runs in console                             | `Demo.java` with role menus                          |
| Documentation HTML 5–10 classes                  | `./gradlew javadoc` → `build/docs/javadoc/`         |

---

## 6. Custom exceptions

```java
public class LowHIndexException extends Exception { … }
public class NotResearcherException extends Exception { … }
public class AuthException extends Exception { … }
public class CreditLimitException extends Exception { … }
```

Demonstrated in seed:
- `tutorResearcher` (h=2) assigned as supervisor to `amir` (year=4) → `LowHIndexException` caught.
- Wrong password during login → `AuthException`.
- Registering for a course past 21 credits → `CreditLimitException`.
- Adding a non-Researcher to a project → `NotResearcherException`.

---

## 7. Authentication flow

```
Demo main loop → [1] Login
                 → AuthService.login(username, password)
                 → success: User dispatched to role-specific menu
                 → wrong creds: AuthException printed
```

All seed accounts use `username == password` for demo convenience:
`admin/admin`, `dean/dean`, `prof/prof`, `tutor/tutor`,
`liana/liana`, `bob/bob`, `amir/amir`.

---

## 8. UML Diagrams

- **Use-case diagram** — `diagrams/UseCaseDiagram.jpg`
- **Class diagram (before applying patterns)** — `diagrams/ClassDiagram_BeforePattern.jpg`
- **Class diagram (after applying patterns)** — `diagrams/ClassDiagram_AfterPattern.jpg`
- **Object diagram** — `diagrams/ObjectDiagram.jpg`
  (draft description in `docs/ObjectDiagram_DRAFT.md`)

---

## 9. Sample demo run (screenshots)

> [Insert screenshot 1: launching `./gradlew run`, seeing seed events + main menu]
>
> [Insert screenshot 2: Student `liana` login → registerForCourse → viewTranscript]
>
> [Insert screenshot 3: Teacher `tutor` login → put mark for `bob`]
>
> [Insert screenshot 4: Admin `admin` login → View logs]
>
> [Insert screenshot 5: Teacher `prof` → Researcher menu → printPapers by citations / by date / by pages]
>
> [Insert screenshot 6: `./gradlew javadoc` then opening `build/docs/javadoc/index.html` in browser]

---

## 10. Project management notes

- **Team chat:** Teams group (screenshot below).
- **Repository:** Git, two branches (`master` for the alternative architecture, `main` for the final
  submission). Final ZIP is built from `main`.
- **Time pressure:** the project was finalized in the last hours before the deadline. A first
  pass on `master` produced an over-engineered SQL-backed skeleton that was later set aside in
  favour of the simpler, fully working domain model on `main`.
- **Problems faced & resolved:**
  - Two parallel branches with incompatible architectures → consolidated on `main`.
  - Mocked vs. real persistence — chose Java serialization for simplicity.
  - Custom exceptions design — went with checked exceptions to make the menu code explicit.
  - Researcher modelling ("interface? abstract? decorator?") — solved using the Decorator pattern
    to allow ANY Employee (or Student) to be a Researcher without inheritance pollution.

> [Insert screenshot 7: Teams group chat history]

---

## 11. How to run

```bash
./gradlew run             # interactive console demo
./gradlew javadoc         # generates HTML docs into build/docs/javadoc/
```

Or directly:
```bash
./gradlew compileJava
java -cp build/classes/java/main kz.kbtu.Demo
```

---

## 12. Conclusion

The system fulfils every requirement on the project description checklist:
required classes, 4+ design patterns, custom exceptions, comparators, serialization,
authentication, enumerations, a runnable demo, and HTML documentation. The codebase
is intentionally small and self-contained so each class can be read in the report and
mapped 1-to-1 to the UML diagrams.
