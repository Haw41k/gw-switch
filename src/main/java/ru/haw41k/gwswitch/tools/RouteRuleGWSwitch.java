package ru.haw41k.gwswitch.tools;

import ru.haw41k.gwswitch.config.Config;

import java.util.List;
import java.util.Map;

public class RouteRuleGWSwitch implements GWSwitch {

    private final RouterConsole console;
    private final String comment;

    public RouteRuleGWSwitch(RouterConsole console, Config cfg) {
        this.console = console;
        this.comment = cfg.getComment();
    }

    @Override
    public String getCurrentGw(String clientIp) {
        List<Map<String, String>> rules = getRulesByIp(clientIp);

        return rules.isEmpty()
                ? GWSwitch.DEFAULT_GW
                : rules.get(0).get("table");
    }

    @Override
    public void setGW(String clientIp, String gwId) {
        setDefaultGW(clientIp);
        addRule(clientIp, gwId);
    }

    @Override
    public void setDefaultGW(String clientIp) {

        List<Map<String, String>> rules = getRulesByIp(clientIp);

        for (Map<String, String> rule : rules) removeRuleById(rule.get(".id"));
    }

    private List<Map<String, String>> getRulesByIp(String ip) {

        String query = String.format("/routing/rule/print where src-address=%s/32 and dst-address=0.0.0.0/0", ip);

        return console.execute(query);
    }

    private void removeRuleById(String id) {

        String query = String.format("/routing/rule/remove .id=%s", id);

        console.execute(query);
    }

    private void addRule(String ip, String tableName) {
        String query = String.format(
                "/routing/rule/add src-address=%s/32 dst-address=0.0.0.0/0 action=lookup table=\"%s\" comment=\"%s\"",
                ip, tableName, comment);

        console.execute(query);
    }
}
