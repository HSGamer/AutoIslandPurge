package me.hsgamer.autoislandpurge;

import org.bukkit.Location;
import world.bentobox.bentobox.api.addons.GameModeAddon;

import java.util.ArrayList;
import java.util.List;

public class Settings {
    private final AutoIslandPurge addon;
    private final List<GameModeAddon> enabledGameModes = new ArrayList<>();
    private int offlineDays = 7;
    private long checkTicks = 300;
    private long deleteTicks = 10;
    private Location spawnLocation;

    public Settings(AutoIslandPurge addon) {
        this.addon = addon;
    }

    public void setup() {
        enabledGameModes.clear();
        var config = addon.getConfig();

        var gameModes = config.getStringList("enabled-modes");
        enabledGameModes.addAll(
                addon.getPlugin().getAddonsManager().getGameModeAddons().stream()
                        .filter(gameModeAddon -> gameModes.contains(gameModeAddon.getDescription().getName()))
                        .toList()
        );
        offlineDays = config.getInt("offline-days-until-purge", offlineDays);
        checkTicks = config.getLong("check-purge-ticks", checkTicks);
        deleteTicks = config.getLong("ticks-per-island-deleted", deleteTicks);
        try {
            var section = config.getConfigurationSection("spawn-location");
            if (section != null) {
                var map = section.getValues(false);
                spawnLocation = Location.deserialize(map);
            }
        } catch (Exception e) {
            // IGNORED
        }
    }

    public List<GameModeAddon> getEnabledGameModes() {
        return enabledGameModes;
    }

    public int getOfflineDays() {
        return offlineDays;
    }

    public long getCheckTicks() {
        return checkTicks;
    }

    public long getDeleteTicks() {
        return deleteTicks;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }
}
