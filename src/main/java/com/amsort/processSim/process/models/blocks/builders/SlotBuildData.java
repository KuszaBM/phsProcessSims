package com.amsort.processSim.process.models.blocks.builders;

public class SlotBuildData {
    int id;
    String type;

    public SlotBuildData(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public SlotBuildData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
