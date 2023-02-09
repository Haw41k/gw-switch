package ru.haw41k.gwswitch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.haw41k.gwswitch.config.model.Router;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties("gw-switch")
public class Config {
    private String mode = "route-tables";
    private List<Router> routers = new ArrayList<>();

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<Router> getRouters() {
        return routers;
    }

    public void setRouters(List<Router> routers) {
        this.routers = routers;
    }

    @Override
    public String toString() {
        return "Config{" +
                "mode='" + mode + '\'' +
                ", routers=" + routers +
                '}';
    }
}
