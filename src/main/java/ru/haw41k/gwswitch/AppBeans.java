package ru.haw41k.gwswitch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.haw41k.gwswitch.config.Config;
import ru.haw41k.gwswitch.config.model.Router;
import ru.haw41k.gwswitch.tools.GWSwitch;
import ru.haw41k.gwswitch.tools.MikrotikRouterConsole;
import ru.haw41k.gwswitch.tools.RouteRuleGWSwitch;
import ru.haw41k.gwswitch.tools.RouterConsole;

@Configuration
public class AppBeans {

    @Bean
    public RouterConsole getRouterConsole(Config cfg) {

        Router router = cfg.getRouters().get(0);
        return new MikrotikRouterConsole(router.getIp(), router.getUser(), router.getPassword());
    }

    @Bean
    public GWSwitch getGWSwitch(RouterConsole console) {
        return new RouteRuleGWSwitch(console);
    }
}
