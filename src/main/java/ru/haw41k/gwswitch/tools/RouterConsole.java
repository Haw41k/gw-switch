package ru.haw41k.gwswitch.tools;

import java.util.List;
import java.util.Map;

public interface RouterConsole {
    List<Map<String, String>> execute(String cmd);
}
