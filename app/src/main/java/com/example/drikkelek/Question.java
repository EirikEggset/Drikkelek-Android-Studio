package com.example.drikkelek;

public class Question {
    private final String type;
    private final String content;
    private String help;
    private String title;
    private static int totalQuestions;

    public Question(String type,String title, String content) {
        this.type = type;
        this.content = content;
        this.title = title;

        totalQuestions++;
    }


    public String getTitle() {
        return title;
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
            case "Vanlig":
                return "#FB6580"; //red
            case "Pekelek":
                return "#50D2C9"; //blue
            case "Tommel opp/ned":
                return "#50D263"; //green
            case "Regel":
                return "#B475DA"; //purple
            case "Kategorier":
                return "#BFD250"; //yellow
        }
        return "@color/main_accent";
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
