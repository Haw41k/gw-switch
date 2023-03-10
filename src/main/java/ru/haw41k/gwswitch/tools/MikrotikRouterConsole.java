package ru.haw41k.gwswitch.tools;

import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.ApiConnectionException;
import me.legrange.mikrotik.MikrotikApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MikrotikRouterConsole implements RouterConsole {
    private String ip;
    private String user;
    private String password;

    private int apiPort = 8728;
    private int tlsApiPort = 8729;
    private boolean tlsEnabled = false;

    private ApiConnection connection;
    private final Logger log = LoggerFactory.getLogger(MikrotikRouterConsole.class);

    public MikrotikRouterConsole(String ip, String user, String password) {
        this.ip = ip;
        this.user = user;
        this.password = password;
    }

    @Override
    public List<Map<String, String>> execute(String cmd) {

        try {
            if (!checkConnection()) updateConnection();

            return connection.execute(cmd);

        } catch (MikrotikApiException e) {
            closeConnectionSafe();

            log.error(e.getMessage());

            return Collections.emptyList();
        }
    }

    private ApiConnection createConnection() throws MikrotikApiException {

        ApiConnection con;

        if (tlsEnabled) {
            con = ApiConnection.connect(
                    SSLSocketFactory.getDefault(), ip, tlsApiPort, 2000);
        } else {
            con = ApiConnection.connect(
                    SocketFactory.getDefault(), ip, apiPort, 2000);
        }

        con.setTimeout(3000);
        con.login(user, password);

        return con;
    }

    private boolean checkConnection() {
        // ApiConnection.isConnected() - работает неправильно, когда соединение потеряно, всё равно возращает true.

        return connection != null && connection.isConnected();
    }

    private void updateConnection() throws MikrotikApiException {
        connection = createConnection();
    }

    private void closeConnectionSafe() {
        if (checkConnection()) {
            try {
                connection.close();
            } catch (ApiConnectionException ex) {
                log.info(ex.getMessage());
            }
        }
    }

    public void setApiPort(int apiPort) {
        this.apiPort = apiPort;
    }

    public void setTlsApiPort(int tlsApiPort) {
        this.tlsApiPort = tlsApiPort;
    }

    public void setTlsEnabled(boolean tlsEnabled) {
        this.tlsEnabled = tlsEnabled;
    }
}
