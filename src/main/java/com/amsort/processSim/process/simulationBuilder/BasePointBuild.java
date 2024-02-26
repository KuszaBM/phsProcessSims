package com.amsort.processSim.process.simulationBuilder;

public class BasePointBuild {
    int id;
    String name;
    String type;
    Object data;

    public BasePointBuild(int id, String name, String type, Object data) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public BasePointBuild() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BasePointBuild{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
