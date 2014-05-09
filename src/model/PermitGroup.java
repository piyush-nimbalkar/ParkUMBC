package model;

public class PermitGroup {

    private String name;
    private String letter;
    private String color;

    public PermitGroup(String name, String letter, String color) {
        this.name = name;
        this.letter = letter;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getLetter() {
        return letter;
    }

    public String getColor() {
        return color;
    }

}
