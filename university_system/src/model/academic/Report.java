package model.academic;

import java.time.LocalDate;
import java.util.Map;

public class Report {
    private String title;
    private Map<String, Double> data;
    private LocalDate generatedDate;

    public Report(String title, Map<String, Double> data) {
        this.title = title;
        this.data = data;
        this.generatedDate = LocalDate.now();
    }

    public String getTitle() {
        return title;
    }

    public Map<String, Double> getData() {
        return data;
    }

    public LocalDate getGeneratedDate() {
        return generatedDate;
    }

    // Здесь можно будет собирать статистику по оценкам или успеваемости.
    public void generate() {
        System.out.println("Report \"" + title + "\" was generated on " + generatedDate);
    }

    public void print() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Report{" +
                "title='" + title + '\'' +
                ", data=" + data +
                ", generatedDate=" + generatedDate +
                '}';
    }
}
