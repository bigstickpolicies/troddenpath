package com.bigstickpolicies.troddenpath;

import com.bigstickpolicies.troddenpath.tread.BlockTreadBehavior;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class TroddenPathConfigurer {
    private List<World> worlds=new ArrayList();
    private List<BlockTreadBehavior> behaviors=new ArrayList();
    private List<Class<? extends Entity>> entityClasses=new ArrayList();
    private List<GameMode> validGameModes=new ArrayList();
    private List<Material> destroyableMaterials=new ArrayList();
    private boolean leatherBootsPreventTrampling;
    public static final double CHANCE_FACTOR=0.03;
    public TroddenPathConfigurer(FileConfiguration config) {
        init(config);
    }
    public void init(FileConfiguration config) {
        config.getList("worlds").iterator().forEachRemaining((x) -> {
            if(x instanceof String) {
                worlds.add(Bukkit.getWorld((String) x));
            }
        });
        var factor=config.getDouble("tread-speed")*CHANCE_FACTOR;
        config.getConfigurationSection("blocks").getKeys(false).forEach((key) -> {

            var subsection=config.getConfigurationSection("blocks."+key);
            subsection.getKeys(false).forEach((k2)-> {
                var cpt=subsection.getDouble(k2+".chance")*factor;
                behaviors.add(new BlockTreadBehavior(Material.matchMaterial(key),
                        Material.matchMaterial(k2),
                        cpt));
            });
        });
        config.getList("entities").iterator().forEachRemaining((s) -> {
            if(s instanceof String) {
                var ec=EntityType.valueOf((String) s).getEntityClass();
                entityClasses.add(ec);
            }
        });
        config.getList("gamemodes").iterator().forEachRemaining((g) -> {
            if(g instanceof String) {
                var gm=GameMode.valueOf((String) g);
                validGameModes.add(gm);
            }
        });
        config.getList("destroyable-blocks").iterator().forEachRemaining((g) -> {
            if(g instanceof String) {
                var m=Material.matchMaterial((String) g);
                if(m==null) {
                    System.out.println(ChatColor.RED+"Error: Not a valid block type "+g);
                    return;
                }
                destroyableMaterials.add(m);
            }
        });
        leatherBootsPreventTrampling=config.getBoolean("leather-boots-prevent-trampling");
    }

    public List<World> getWorlds() {
        return worlds;
    }

    public List<BlockTreadBehavior> getBehaviors() {
        return behaviors;
    }

    public List<Class<? extends Entity>> getEntityClasses() {
        return entityClasses;
    }
    public List<GameMode> getValidGameModes() {return validGameModes;}
    public boolean getLeatherBootsPreventTrampling() {
        return leatherBootsPreventTrampling;
    }
    public List<Material> getDestroyableMaterials() {
        return destroyableMaterials;
    }
}
