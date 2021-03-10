package com.example.drikkelek;

public class Pack {
    private String packName;
    private String activityType;
    private String[] files;

    public Pack(String packName, String activityType, String[] files) {
        this.packName = packName;
        this.activityType = activityType;
        this.files = files;
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

    public String[] getFiles() {
        return files;
    }
}
