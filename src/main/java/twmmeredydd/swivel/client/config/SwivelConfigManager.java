package twmmeredydd.swivel.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import twmmeredydd.swivel.client.SwivelClient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SwivelConfigManager {
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("swivel.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static SwivelConfig config = new SwivelConfig();

    public static void load() {
        if (Files.exists(CONFIG_PATH)){
            try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_PATH.toFile()))) {
                config = GSON.fromJson(reader, SwivelConfig.class);
            } catch (IOException e) {
                SwivelClient.LOGGER.error("Unable to load Swivel config");
                e.printStackTrace();
            }
        }

        save();
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            SwivelClient.LOGGER.error("Unable to save Swivel config");
        }
    }

    public static SwivelConfig getConfig() {
        return config;
    }
}
