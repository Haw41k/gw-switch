package ru.haw41k.gwswitch.config.model;

import java.util.ArrayList;
import java.util.List;

public class Router {
    private String ip;
    private String user;
    private String password;
    private List<Gateway> gateways = new ArrayList<>();

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Gateway> getGateways() {
        return gateways;
    }

    public void setGateways(List<Gateway> gateways) {
        this.gateways = gateways;
    }

    @Override
    public String toString() {
        return "Router{" +
                "ip='" + ip + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", gateways=" + gateways +
                '}';
    }
}
