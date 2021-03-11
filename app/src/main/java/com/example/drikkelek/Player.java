package com.example.drikkelek;

public class Player {
    private String name;
    private boolean isPlayer = true;
    private static int counter = 0;

    public Player(String name) {
        this.name = name;
        counter++;
    }

    public Player() {
        name = "";
        isPlayer = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getCounter() {
        return counter;
    }
}
