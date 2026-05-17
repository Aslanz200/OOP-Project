package kz.kbtu.services;

import kz.kbtu.models.academic.Course;
import kz.kbtu.models.academic.Mark;
import kz.kbtu.models.research.ResearchPaper;
import kz.kbtu.models.research.Researcher;
import kz.kbtu.patterns.Database;

import java.util.Calendar;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Generates simple academic reports + research statistics required by spec.
 */
public class ReportService {

    /** Quick stats over all marks (avg/min/max + A/B/C/D/F counts). */
    public String gradeStatistics() {
        List<Mark> marks = Database.getInstance().getMarks();
        if (marks.isEmpty()) return "No marks recorded.";

        DoubleSummaryStatistics stats = marks.stream()
                .mapToDouble(Mark::getTotal).summaryStatistics();

        Map<String, Long> letters = marks.stream()
                .collect(Collectors.groupingBy(Mark::getLetterGrade, Collectors.counting()));

        StringBuilder sb = new StringBuilder();
        sb.append("=== Grade report ===\n");
        sb.append(String.format("Count: %d, avg=%.2f, min=%.2f, max=%.2f%n",
                stats.getCount(), stats.getAverage(), stats.getMin(), stats.getMax()));
        sb.append("Letter distribution: ").append(letters).append('\n');
        return sb.toString();
    }

    public String courseStatistics(Course course) {
        List<Mark> marks = Database.getInstance().getMarks().stream()
                .filter(m -> m.getCourse().equals(course)).collect(Collectors.toList());
        if (marks.isEmpty()) return "No marks for " + course.getCourseId();
        double avg = marks.stream().mapToDouble(Mark::getTotal).average().orElse(0);
        long fails = marks.stream().filter(Mark::isFail).count();
        return "Course " + course.getCourseId() + ": avg=" + String.format("%.2f", avg) +
                ", fails=" + fails + "/" + marks.size();
    }

    /** Print all papers across the university, sorted by the supplied Comparator. */
    public void printAllPapers(Comparator<ResearchPaper> comparator) {
        System.out.println("=== All papers in the university ===");
        Database.getInstance().getPapers().stream()
                .sorted(comparator)
                .forEach(p -> System.out.println("  " + p));
    }

    /** Top cited researcher across all schools. */
    public Optional<Researcher> topCitedResearcher(List<Researcher> researchers) {
        return researchers.stream()
                .max(Comparator.comparingInt(r -> r.getPapers().stream()
                        .mapToInt(ResearchPaper::getCitations).sum()));
    }

    /** Top cited researcher whose paper of the given year has the most citations. */
    public Optional<Researcher> topCitedOfYear(List<Researcher> researchers, int year) {
        return researchers.stream()
                .max(Comparator.comparingInt(r -> r.getPapers().stream()
                        .filter(p -> {
                            if (p.getDatePublished() == null) return false;
                            Calendar c = Calendar.getInstance();
                            c.setTime(p.getDatePublished());
                            return c.get(Calendar.YEAR) == year;
                        })
                        .mapToInt(ResearchPaper::getCitations).sum()));
    }
}
