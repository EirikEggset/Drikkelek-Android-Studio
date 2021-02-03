package com.example.app;

public class Question {
    private final String type;
    private final String content;
    private  String help;
    private final String[] categories;
    private   static int totalQuestions;

    public Question(String type, String content, String[] categories) {
        this.type = type;
        this.content = content;
        this.categories = categories;

        totalQuestions++;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String getHelp() {
        return help;
    }

    public String getColor() {
        switch (type) {
            case "Spørsmål":
                return "#FB6580"; //red
            case "Pekelek":
                return "#0065C1"; //blue
            case "Tommel opp/ned":
                return "#00DC0C"; //green
        }
        return "@color/main_accent";
    }

    public String[] getCategories() {
        return categories;
    }

    public static int getTotalQuestions() {
        return totalQuestions;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public static void setTotalQuestions(int totalQuestions) {
        Question.totalQuestions = totalQuestions;
    }
}
