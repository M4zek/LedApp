package pl.mazi.ledapp.model;

import java.util.ArrayList;
import java.util.List;

public class PatternModel {

    public enum Type{
        SINGLE,
        CUSTOM
    }

    private Type type;

    private String title;

    private List<String> patternOptions;

    public PatternModel(Type type, String title) {
        this.type = type;
        this.title = title;
    }

    public PatternModel(Type type, String title, List<String> patternOptions) {
        this.type = type;
        this.title = title;
        this.patternOptions = patternOptions;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getPatternOptions() {
        return patternOptions;
    }

    public void setPatternOptions(List<String> patternOptions) {
        this.patternOptions = patternOptions;
    }
}
