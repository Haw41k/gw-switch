package ru.haw41k.gwswitch.tools;

import ru.haw41k.gwswitch.config.Config;

import java.util.List;
import java.util.Map;

public class FirewallAddressListsCleaner implements Cleaner {
    private final RouterConsole console;
    private final String comment;

    public FirewallAddressListsCleaner(RouterConsole console, Config cfg) {
        this.console = console;
        this.comment = cfg.getComment();
    }

    @Override
    public void clean() {

        List<Map<String, String>> items = getListForClean();

        for (Map<String, String> item : items) {
            console.execute(String.format("/ip/firewall/address-list/remove .id=%s", item.get(".id")));
        }
    }

    private List<Map<String, String>> getListForClean() {

        String query = String.format("/ip/firewall/address-list/print where comment=\"%s\"", comment);

        return console.execute(query);
    }
}
