package ru.haw41k.gwswitch;

import me.legrange.mikrotik.MikrotikApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.haw41k.gwswitch.config.Config;
import ru.haw41k.gwswitch.config.model.Gateway;
import ru.haw41k.gwswitch.tools.GWSwitch;
import ru.haw41k.gwswitch.tools.HttpServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/gw")
public class AppRestController {
    @Autowired
    GWSwitch gwSwitch;

    @Autowired
    Config cfg;

    @GetMapping
    public List<Gateway> getAllGW() {
        return cfg.getRouters().get(0).getGateways();
    }

    @GetMapping("/current")
    public Gateway getGW(HttpServletRequest request) throws MikrotikApiException {

        String clientIP = HttpServletRequestUtils.getRealClientIp(request);

        String gwId = gwSwitch.getCurrentGw(clientIP);

        Gateway gw = new Gateway();
        gw.setId(gwId);

        return gw;
    }

    @PostMapping("/current")
    public void setGW(HttpServletRequest request, @RequestBody Gateway gw) throws MikrotikApiException {

        String clientIP = HttpServletRequestUtils.getRealClientIp(request);

        gwSwitch.setGW(clientIP, gw.getId());
    }

    @DeleteMapping("/current")
    public void setDefaultGW(HttpServletRequest request) throws MikrotikApiException {

        String clientIP = HttpServletRequestUtils.getRealClientIp(request);

        gwSwitch.setDefaultGW(clientIP);
    }
}
