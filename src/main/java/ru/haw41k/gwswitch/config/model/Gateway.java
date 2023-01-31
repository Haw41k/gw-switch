package ru.haw41k.gwswitch.config.model;

public class Gateway {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Gateway{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
