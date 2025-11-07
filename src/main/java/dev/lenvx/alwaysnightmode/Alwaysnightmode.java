package dev.lenvx.alwaysnightmode;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public final class Alwaysnightmode extends JavaPlugin {

    @Override
    public void onEnable() {
        // save the default config if it doesn't exist
        saveDefaultConfig();

        // schedule a repeating task to apply night vision
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            List<String> excludedPlayers = getConfig().getStringList("excluded-players");
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!excludedPlayers.contains(player.getName())) {
                    // apply night vision with infinite duration and no particles
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
                } else {
                    // remove night vision from excluded players if they somehow got it
                    player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                }
            }
        }, 0L, 20L * 5); // run every 5 seconds (20 ticks per second)
    }
// if your reading my code and my comments, hi!! :) apperciate you if you wanna contributee
    @Override
    public void onDisable() {
        // remove night vision from all players when the plugin is disabled
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
    }
}
