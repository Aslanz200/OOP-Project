# Object Diagram — текстовый черновик для StarUML

Эту диаграмму нужно нарисовать в StarUML как **Object Diagram** (Model → Add → Object Diagram).
Это snapshot конкретных объектов системы во время выполнения demo (после seed-данных).

## Объекты для рисования

### Пользователи
```
admin : Admin
  id          = "U1"
  username    = "admin"
  firstName   = "Aizhan"
  lastName    = "Kozhamuratova"
  employeeId  = "E1"
  department  = "ADM"

dean : Manager
  id          = "U2"
  managerType = DEAN
  department  = "CS"

prof : Teacher
  id          = "U3"
  title       = PROFESSOR
  department  = "CS"

tutor : Teacher
  id          = "U4"
  title       = TUTOR
  department  = "CS"

liana : Student
  id          = "U5"
  year        = 4
  gpa         = 3.8
  credits     = 12
  failCount   = 0

bob : Student
  id          = "U6"
  year        = 2
  gpa         = 3.1
  credits     = 6

amir : Student
  id          = "U7"
  year        = 4
  gpa         = 3.4
  credits     = 5
```

### Курсы
```
oop : Course
  courseId = "CSCI152"
  name     = "Object-Oriented Programming"
  credits  = 6

alg : Course
  courseId = "CSCI235"
  name     = "Algorithms"
  credits  = 6

db2 : Course
  courseId = "CSCI275"
  name     = "Databases"
  credits  = 5
```

### Lessons
```
lec_oop  : Lesson { type = LECTURE,  course = oop }
prac_oop : Lesson { type = PRACTICE, course = oop }
lec_alg  : Lesson { type = LECTURE,  course = alg }
```

### Marks
```
m1 : Mark { student=liana, course=oop, att1=92, att2=88, finalExam=95, total≈91.4, grade=A }
m2 : Mark { student=liana, course=alg, att1=80, att2=75, finalExam=70, total≈74.5, grade=B }
m3 : Mark { student=bob,   course=oop, att1=55, att2=60, finalExam=45, total≈52.5, grade=D }
m4 : Mark { student=amir,  course=db2, att1=70, att2=72, finalExam=68, total≈69.8, grade=C }
```

### Researchers (Decorator pattern)
```
profResearcher : ResearcherDecorator
  wraps   = prof
  hIndex  = 7
  papers  = [p1, p2]

tutorResearcher : ResearcherDecorator
  wraps   = tutor
  hIndex  = 2          // < 3 — нельзя назначить супервайзером
  papers  = [p3]

adminResearcher : ResearcherDecorator
  wraps   = admin      // ← демонстрирует "employee, который не Teacher и не Student, но Researcher"
  hIndex  = 4
  papers  = [p4]
```

### Papers
```
p1 : ResearchPaper {title="Compositional ML for Education",  authors=[Aitbayev,Smirnova], journal="IEEE Access",      pages=12, date=2025-03-12, citations=145}
p2 : ResearchPaper {title="Graph Algorithms Revisited",      authors=[Aitbayev],          journal="ACM TOPLAS",        pages=24, date=2024-11-05, citations=67}
p3 : ResearchPaper {title="Tutoring at Scale",               authors=[Nurlanov],          journal="EduTech",           pages=8,  date=2025-01-20, citations=22}
p4 : ResearchPaper {title="Admin Workflows in Universities", authors=[Kozhamuratova],     journal="Higher Ed Today",   pages=16, date=2023-06-15, citations=51}
```

### Project
```
aiProject : ResearchProject
  topic        = "AI in Education"
  participants = [profResearcher, tutorResearcher]
  papers       = [p1, p3]
```

### Database (Singleton)
```
db : Database
  users    = [admin, dean, prof, tutor, liana, bob, amir]
  courses  = [oop, alg, db2]
  marks    = [m1, m2, m3, m4]
  papers   = [p1, p2, p3, p4]
  projects = [aiProject]
  news     = ["Welcome to the new academic year!"]
```

## Связи (links между объектами)

Рисуй сплошные линии (links):
- `liana ── enrolled ──> oop`
- `liana ── enrolled ──> alg`
- `bob   ── enrolled ──> oop`
- `amir  ── enrolled ──> db2`
- `liana ── hasSupervisor ──> profResearcher`   ← важно: 4 курс с h=7 ✓
- `prof  ── manages ──> oop`
- `prof  ── manages ──> alg`
- `tutor ── manages ──> db2`
- `tutor ── manages ──> oop`                    ← демо: >1 преподаватель на курс
- `m1 ── student ──> liana`,  `m1 ── course ──> oop`  (аналогично для m2/m3/m4)
- `profResearcher ── decorates ──> prof` (можно пунктиром)
- `tutorResearcher ── decorates ──> tutor`
- `adminResearcher ── decorates ──> admin`
- `aiProject ── participant ──> profResearcher`
- `aiProject ── participant ──> tutorResearcher`
- `db (singleton) ── содержит ──> всё перечисленное`

## Совет по компоновке в StarUML
- Сверху: `db : Database` как корневой узел
- Слева колонкой: пользователи (admin, dean, prof, tutor, liana, bob, amir)
- В центре: курсы и оценки
- Справа: researchers + projects + papers
- Линки рисовать тонко, чтобы не путалось

Сохрани картинку в `diagrams/ObjectDiagram.jpg` и положи в ZIP.
