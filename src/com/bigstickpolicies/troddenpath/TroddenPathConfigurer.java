package com.bigstickpolicies.troddenpath;

import com.bigstickpolicies.troddenpath.tread.BlockEffect;
import com.bigstickpolicies.troddenpath.tread.BlockTreadSheet;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.*;

public class TroddenPathConfigurer {
    private Set<String> worlds=new HashSet();
    private Map<String,BlockTreadSheet> treadBehaviors =new HashMap();
    private List<Class<? extends Entity>> entityClasses=new ArrayList();
    private List<GameMode> validGameModes=new ArrayList();
    private Set<Material> destroyableMaterials=new HashSet();
    private Set<String> enabledBoots=new HashSet();
    private final String[] behaviorTypes=new String[]{"base","scorchers","titan_boots","seedlayers"};
    private boolean leatherBootsPreventTrampling;
    public static final double CHANCE_FACTOR=0.03;
    public static double factor;
    public TroddenPathConfigurer(FileConfiguration config) {
        init(config);
    }
    public BlockTreadSheet parseBehaviors(ConfigurationSection config) {
        var sheet=new BlockTreadSheet();
        config.getKeys(false).forEach((key) -> {

            var subsection=config.getConfigurationSection(key);
            subsection.getKeys(false).forEach((k2)-> {
                var cpt=subsection.getDouble(k2+".chance")*factor;
                sheet.add(Material.matchMaterial(key),new BlockEffect(Material.matchMaterial(k2),cpt));
            });
        });
        return sheet;
    }
    public void init(FileConfiguration config) {
        config.getList("worlds").iterator().forEachRemaining((x) -> {
            if(x instanceof String) {
                worlds.add((String) x);
            }
        });
        factor=config.getDouble("tread-speed")*CHANCE_FACTOR;

        for(var s:behaviorTypes) {
            treadBehaviors.put(s,parseBehaviors(config.getConfigurationSection("tread-behaviors."+s)));
        }
        System.out.println(treadBehaviors);
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


        destroyableMaterials.add(Material.AIR);
        destroyableMaterials.add(Material.CAVE_AIR);
        destroyableMaterials.add(Material.VOID_AIR);
        leatherBootsPreventTrampling=config.getBoolean("leather-boots-prevent-trampling");

        config.getConfigurationSection("crafts").getKeys(false).forEach((s) -> {
            var sec=config.getConfigurationSection("crafts."+s);
            if(sec.getBoolean("enabled")) {
                enabledBoots.add(s);
            };
        });
    }

    public Set<String> getWorldNames() {
        return worlds;
    }

    public BlockTreadSheet getTreadBehavior(String s) {
        return treadBehaviors.get(s);
    }

    public List<Class<? extends Entity>> getEntityClasses() {
        return entityClasses;
    }
    public List<GameMode> getValidGameModes() {return validGameModes;}
    public boolean getLeatherBootsPreventTrampling() {
        return leatherBootsPreventTrampling;
    }

    public boolean isDestroyable(Material m) {
        return destroyableMaterials.contains(m);
    }

    public boolean getCraftEnabled(String s) {
        return enabledBoots.contains(s);
    }
}
