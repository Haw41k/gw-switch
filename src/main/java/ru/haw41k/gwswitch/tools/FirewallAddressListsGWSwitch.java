package ru.haw41k.gwswitch.tools;

import ru.haw41k.gwswitch.config.Config;
import ru.haw41k.gwswitch.config.model.Gateway;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FirewallAddressListsGWSwitch implements GWSwitch {
    private final RouterConsole console;
    private final List<Gateway> gateways;
    private final String listFilter;
    private final String comment;

    public FirewallAddressListsGWSwitch(RouterConsole console, Config cfg) {

        this.console = console;
        this.gateways = cfg.getRouters().get(0).getGateways();
        this.listFilter = buildListFilter(gateways);
        this.comment = cfg.getComment();
    }

    @Override
    public String getCurrentGw(String clientIp) {

        List<Map<String, String>> rules = getListsByIp(clientIp);

        return rules.isEmpty()
                ? GWSwitch.DEFAULT_GW
                : rules.get(0).get("list");
    }

    @Override
    public void setGW(String clientIp, String gwId) {

        setDefaultGW(clientIp);
        addIPtoList(clientIp, gwId);
    }

    @Override
    public void setDefaultGW(String clientIp) {

        List<Map<String, String>> lists = getListsByIp(clientIp);

        for (Map<String, String> list : lists) removeFromListById(list.get(".id"));
    }

    private List<Map<String, String>> getListsByIp(String ip) {

        if (gateways.isEmpty()) {
            return Collections.emptyList();
        }

        String query = String.format("/ip/firewall/address-list/print where address=%s and (%s)", ip, listFilter);

        return console.execute(query);
    }

    private void addIPtoList(String ip, String listName) {

        String query = String.format(
                "/ip/firewall/address-list/add address=%s list=\"%s\" comment=\"%s\"",
                ip, listName, comment);

        console.execute(query);
    }

    private void removeFromListById(String id) {

        String query = String.format("/ip/firewall/address-list/remove .id=%s", id);

        console.execute(query);
    }

    private String buildListFilter(List<Gateway> gateways) {

        Iterator<Gateway> iterator = gateways.iterator();

        StringBuilder sb = new StringBuilder(String.format("list=\"%s\"", iterator.next().getId()));

        while (iterator.hasNext()) {
            sb.append(String.format(" or list=\"%s\"", iterator.next().getId()));
        }

        return sb.toString();
    }
}
