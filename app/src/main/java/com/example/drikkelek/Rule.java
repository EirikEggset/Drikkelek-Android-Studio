package com.example.drikkelek;

public class Rule {
    private String rule;

    public Rule(String rule) {
        this.rule = rule;
    }

    @Override
    public String toString() {
        return rule;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
