package gg.slvr.magedemo;

import gg.slvr.magedemo.listener.InventoryListener;
import gg.slvr.magedemo.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class MageDemo extends JavaPlugin {

    private static MageDemo instance;

    private final HashMap<String, PlayerInfo> playerInfo = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public PlayerInfo getPlayerInfo(String playerName) {
        return getPlayerInfo(playerName, false);
    }

    public PlayerInfo getPlayerInfo(String playerName, boolean update) {
        Player player = Bukkit.getServer().getPlayer(playerName);
        PlayerInfo info = playerInfo.get(playerName);

        if (info == null) {
            info = new PlayerInfo(player);
            playerInfo.put(playerName, info);
        } else if (update) {
            info.setPlayer(player);
        }

        return info;
    }

    public static MageDemo getInstance() {
        return instance;
    }

}
