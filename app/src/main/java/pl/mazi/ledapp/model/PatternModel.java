package pl.mazi.ledapp.model;

import java.util.List;

public class PatternModel {
    public enum Style{

        COLOR,
        BOX,
        SIMPLE
    }

    private Style style;

    private String title;

    private String description;

    private int icon;

    List<String> subPatternList;


    // Constructors
    public PatternModel(Style style, int icon,String title, String description ,List<String> subPatternList) {
        this.style = style;
        this.title = title;
        this.icon = icon;
        this.subPatternList = subPatternList;
        this.description = description;
    }

    public PatternModel(Style style, int icon,String title, String description) {
        this.style = style;
        this.title = title;
        this.description = description;
        this.icon = icon;
    }

    // GETTERS & SETTERS
    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSubPatternList() {
        return subPatternList;
    }

    public void setSubPatternList(List<String> subPatternList) {
        this.subPatternList = subPatternList;
    }
}