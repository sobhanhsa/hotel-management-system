package models;

import java.time.LocalDateTime;

import interfaces.Exportable;

public abstract class Report implements Exportable {

    protected String title;
    protected LocalDateTime generatedAt;

    public Report(String title) {
        this.title = title;
        this.generatedAt = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    @Override
    public abstract String exportToText();
}