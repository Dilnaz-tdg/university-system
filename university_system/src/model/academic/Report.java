package model.academic;

import java.time.LocalDate;
import java.util.Map;


//  Класс Report описывает простой отчёт со статистическими данными.
//  Отчёт хранит название, набор показателей и дату генерации.
public class Report {
    private String title;
    private Map<String, Double> data;
    private LocalDate generatedDate;

//    Создаёт отчёт с названием и данными.
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
//    Отмечает создание отчёта в консоли.
    public void generate() {
        System.out.println("Report \"" + title + "\" was generated on " + generatedDate);
    }

    //    Печатает отчёт в консоль.
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
