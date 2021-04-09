package com.example.drikkelek;

public class Pack {
    private String packName;
    private String activityType;

    public Pack(String packName, String activityType) {
        this.packName = packName;
        this.activityType = activityType;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getPackName() {
        return packName;
    }

    public String getActivityType() {
        return activityType;
    }
}
