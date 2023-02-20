package ru.haw41k.gwswitch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.haw41k.gwswitch.config.Config;
import ru.haw41k.gwswitch.config.model.Router;
import ru.haw41k.gwswitch.tools.*;

@Configuration
@EnableScheduling
public class AppBeans {

    @Bean
    public RouterConsole getRouterConsole(Config cfg) {

        Router router = cfg.getRouters().get(0);
        MikrotikRouterConsole console = new MikrotikRouterConsole(router.getIp(), router.getUser(), router.getPassword());

        console.setTlsEnabled(cfg.isTlsEnabled());

        return console;
    }

    @Bean
    public GWSwitch getGWSwitch(RouterConsole console, Config cfg) {

        if ("ip-lists".equals(cfg.getMode())) {
            return new FirewallAddressListsGWSwitch(console, cfg);

        } else {
            return new RouteRuleGWSwitch(console, cfg);
        }
    }

    @Bean
    public Cleaner getCleaner(RouterConsole console, Config cfg) {

        if ("ip-lists".equals(cfg.getMode())) {
            return new FirewallAddressListsCleaner(console, cfg);

        } else {
            return new RouteRuleCleaner(console, cfg);
        }
    }
}
