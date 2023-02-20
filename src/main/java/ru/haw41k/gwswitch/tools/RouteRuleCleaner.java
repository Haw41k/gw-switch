package ru.haw41k.gwswitch.tools;

import ru.haw41k.gwswitch.config.Config;

import java.util.List;
import java.util.Map;

public class RouteRuleCleaner implements Cleaner {

    private final RouterConsole console;
    private final String comment;

    public RouteRuleCleaner(RouterConsole console, Config cfg) {
        this.console = console;
        this.comment = cfg.getComment();
    }

    @Override
    public void clean() {

        List<Map<String, String>> items = getListForClean();

        for (Map<String, String> item : items) {
            console.execute(String.format("/routing/rule/remove .id=%s", item.get(".id")));
        }
    }

    private List<Map<String, String>> getListForClean() {

        String query = String.format("/routing/rule/print where comment=\"%s\"", comment);

        return console.execute(query);
    }
}
