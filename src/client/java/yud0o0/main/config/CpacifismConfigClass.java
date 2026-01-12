package yud0o0.main.config;

import java.util.ArrayList;
import java.util.List;

public class CpacifismConfigClass extends CpacifismConfigManager {
    public boolean enabled = true;
    public List<String> friends = new ArrayList<>(List.of());

    public CpacifismConfigClass(String path) {
        super(path);
    }
}
