package ru.haw41k.gwswitch.tools;

import javax.servlet.http.HttpServletRequest;

public final class HttpServletRequestUtils {
    private HttpServletRequestUtils() {
    }

    public static String getRealClientIp(HttpServletRequest request) {

        String xff = request.getHeader("X-FORWARDED-FOR");

        if (xff == null || xff.isEmpty()) {

            return request.getRemoteAddr();

        } else {

            return parseXFF(xff);

        }
    }

    private static String parseXFF(String xff) {
        return xff.split(",")[0];
    }

}
