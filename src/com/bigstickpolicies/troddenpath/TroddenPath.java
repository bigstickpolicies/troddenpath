package com.bigstickpolicies.troddenpath;

import com.bigstickpolicies.troddenpath.tread.BlockTreadBehavior;
import com.bigstickpolicies.troddenpath.tread.PathTreader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.logging.Level;

public class TroddenPath extends JavaPlugin {
    public static final String VERSION ="v1.0.0";
    public static PathTreader treader;
    public static TroddenPathConfigurer globalConfigs;
    @Override
    public void onEnable() {
        Bukkit.getLogger().log(Level.INFO, ChatColor.GREEN+"Starting TroddenPath"+ VERSION);
        this.saveDefaultConfig();
        globalConfigs=new TroddenPathConfigurer(this.getConfig());
        //redo

        treader=new PathTreader(this);

    }
    @Override
    public void onDisable() {
        Bukkit.getLogger().log(Level.INFO,ChatColor.GREEN+"Stopping TroddenPath"+VERSION);
    }
}
