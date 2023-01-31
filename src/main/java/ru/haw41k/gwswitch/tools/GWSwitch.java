package ru.haw41k.gwswitch.tools;

import me.legrange.mikrotik.MikrotikApiException;

public interface GWSwitch {
    String DEFAULT_GW = "default-gw";

    String getCurrentGw(String clientIp) throws MikrotikApiException;

    void setGW(String clientIp, String gwId) throws MikrotikApiException;

    void setDefaultGW(String clientIp) throws MikrotikApiException;
}
