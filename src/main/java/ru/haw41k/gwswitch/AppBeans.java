package ru.haw41k.gwswitch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.haw41k.gwswitch.config.Config;
import ru.haw41k.gwswitch.config.model.Router;
import ru.haw41k.gwswitch.tools.*;

@Configuration
public class AppBeans {

    @Bean
    public RouterConsole getRouterConsole(Config cfg) {

        Router router = cfg.getRouters().get(0);
        return new MikrotikRouterConsole(router.getIp(), router.getUser(), router.getPassword());
    }

    @Bean
    public GWSwitch getGWSwitch(RouterConsole console, Config cfg) {

        if ("ip-lists".equals(cfg.getMode())) {
            return new FirewallAddressListsGWSwitch(console, cfg.getRouters().get(0).getGateways());

        } else {
            return new RouteRuleGWSwitch(console);
        }
    }
}
