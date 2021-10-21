package com.example.drikkelek;

public class Question {
    private final String type;
    private final String content;
    private String help;
    private final String gameMode;
    private final String title;
    private String returnTitle;
    private String returnContent;
    private int returnTime;
    private boolean hasHelp = false;
    private boolean hasReturn = false;
    private boolean hasRule = false;
    private boolean isReturnRule = false;
    private static int totalQuestions;
    private Rule rule = new Rule("Tom");

    public Question(String gameMode,String type,String title, String content) {
        this.gameMode = gameMode;
        this.type = type;
        this.content = content;
        this.title = title;

        totalQuestions++;
    }

    public void setReturn(String returnTitle, String returnContent, int returnTime) {
        this.returnTitle = returnTitle;
        this.returnContent = returnContent;
        this.returnTime = returnTime;
        this.hasReturn = true;
    }

    public boolean isHasHelp() {
        return hasHelp;
    }

    public String getTitle() {
        return title;
    }

    public String getGameMode() {
        return gameMode;
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

    public String getReturnTitle() {
        return returnTitle;
    }

    public String getReturnContent() {
        return returnContent;
    }

    public int getReturnTime() {
        return returnTime;
    }

    public boolean ifHasReturn() {
        return hasReturn;
    }

    public String getColor() {
        switch (type) {
            case "Normal":
                return "#FB6580"; //red
            case "Double":
                return "#FB6580"; //red
            case "Point":
                return "#50D2C9"; //blue
            case "Thumbs":
                return "#50D263"; //green
            case "Rule":
                return "#B475DA"; //purple
            case "Category":
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

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
        hasRule = true;
    }

    public boolean hasRule() {
        return hasRule;
    }

    public void setHasRule(boolean bool) {
        hasRule = bool;
    }

    public boolean isReturnRule() {
        return isReturnRule;
    }

    public void setIsReturnRule(Rule rule){
        this.rule = rule;
        isReturnRule = true;
    }
}
