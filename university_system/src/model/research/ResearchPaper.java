package model.research;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ResearchPaper implements Comparable<ResearchPaper> {

    private String title;
    private List<String> authors;
    private String journal;
    private int pages;
    private String doi;
    private int citations;
    private LocalDate publishedDate;

    public ResearchPaper(String title, List<String> authors, String journal, int pages, String doi, int citations, LocalDate publishedDate){

        this.title = title;
        this.authors = authors;
        this.journal = journal;
        this.pages = pages;
        this.doi = doi;
        this.citations = citations;
        this.publishedDate = publishedDate;

    }

    public String getTitle(){ return title; }

    public List<String> getAuthors(){ return authors; }

    public String getJournal(){ return journal; }

    public int getPages(){ return pages; }

    public String getDoi(){ return doi; }

    public int getCitations(){ return citations; }

    public LocalDate getPublishedDate(){ return publishedDate; }

    public void setCitations(int citations){ this.citations = citations; }

    @Override
    public int compareTo(ResearchPaper other){ return Integer.compare(other.citations, this.citations); }

    @Override
    public String toString() {
        return "ResearchPaper{" +
                "title='" + title + '\'' +
                ", authors=" + authors +
                ", journal='" + journal + '\'' +
                ", pages=" + pages +
                ", doi='" + doi + '\'' +
                ", citations=" + citations +
                ", publishedDate=" + publishedDate +
                '}';
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(!(obj instanceof ResearchPaper)) return false;
        ResearchPaper other = (ResearchPaper) obj;
        return Objects.equals(doi, other.doi);
    }

    @Override
    public int hashCode(){ return Objects.hash(doi); }
}
