package ru.haw41k.gwswitch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.haw41k.gwswitch.config.Config;
import ru.haw41k.gwswitch.tools.HttpServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AppController {
    @Autowired
    Config cfg;

    @GetMapping("/")
    public String get(Model model, HttpServletRequest request) {

        String clientIP = HttpServletRequestUtils.getRealClientIp(request);

        model.addAttribute("ip", clientIP);
        model.addAttribute("gateways", cfg.getRouters().get(0).getGateways());

        return "index";
    }
}
