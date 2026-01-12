package yud0o0.main.config;

import yud0o0.main.ChosablePacifism;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

public abstract class CpacifismConfigManager {
    private final transient String CONFPATH;

    public CpacifismConfigManager(String path) {
        CONFPATH = path;
    }

    public void loadOrCreate() {
        File file = new File(CONFPATH);
        if (!file.exists()) {
            File parent = file.getParentFile();
            if (!(parent.exists() || parent.mkdirs()))
                ChosablePacifism.LOGGER.warn("Can't create config: " + parent.getAbsolutePath());
            try {
                saveJsonWithoutCatch();
            } catch (IOException e) {
                ChosablePacifism.LOGGER.warn("Exception occurred while writing new config. " + e);
            }
        } else {
            try (FileReader f = new FileReader(CONFPATH)) {
                CpacifismConfigManager c = new Gson().fromJson(f, getClass());
                for (Field field : getClass().getDeclaredFields()) field.set(this, field.get(c));
            } catch (IOException | IllegalAccessException e) {
                ChosablePacifism.LOGGER.warn("Exception occurred while reading config. " + e);
            }
        }
    }

    public void saveJson() {
        try {
            saveJsonWithoutCatch();
        } catch (IOException e) {
            ChosablePacifism.LOGGER.warn("Exception occurred while saving config. " + e);
        }
    }

    public void saveJsonWithoutCatch() throws IOException {
        try (FileWriter w = new FileWriter(CONFPATH)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(this, w);
            ChosablePacifism.LOGGER.info("Config saved: " + CONFPATH);
        }
    }

    public void reset() {
        try {
            CpacifismConfigManager n = getClass().getConstructor(String.class).newInstance(CONFPATH);
            for (Field field : getClass().getDeclaredFields()) field.set(this, field.get(n));
        } catch (Exception e) {
            ChosablePacifism.LOGGER.warn("Exception occurred while resetting config. " + e);
        }
    }
}