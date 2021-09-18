package com.bigstickpolicies.troddenpath;

import com.bigstickpolicies.troddenpath.items.BootsRegistry;
import com.bigstickpolicies.troddenpath.items.ItemInitializer;
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
    public static TroddenPath plugin;
    public static final String VERSION ="v1.0.0";
    public static PathTreader treader;
    public static TroddenPathConfigurer globalConfigs;
    public static BootsRegistry bootsRegistry;
    @Override
    public void onEnable() {
        plugin=this;
        Bukkit.getLogger().log(Level.INFO, ChatColor.YELLOW+"Starting TroddenPath"+ VERSION);
        this.saveDefaultConfig();

        globalConfigs=new TroddenPathConfigurer(this.getConfig());
        //redo
        bootsRegistry=new BootsRegistry();
        (new ItemInitializer()).create();

        treader=new PathTreader(this);

    }
    @Override
    public void onDisable() {
        Bukkit.getLogger().log(Level.INFO,ChatColor.YELLOW+"Stopping TroddenPath"+VERSION);
    }
}
