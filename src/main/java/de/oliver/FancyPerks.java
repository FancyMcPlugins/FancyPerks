package de.oliver;

import org.bukkit.plugin.java.JavaPlugin;

public class FancyPerks extends JavaPlugin {

    private static FancyPerks instance;

    public FancyPerks() {
        instance = this;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public static FancyPerks getInstance() {
        return instance;
    }
}
