package com.bigstickpolicies.troddenpath.tread;

import com.bigstickpolicies.troddenpath.TroddenPath;
import com.bigstickpolicies.troddenpath.TroddenPathConfigurer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PathTreader {
    TroddenPath plugin;
    private final Map<Material, List<BlockTreadBehavior>> blockBehaviorMap=new HashMap();
    public PathTreader(TroddenPath plugin, TroddenPathConfigurer config) {
        var behaviors=config.getBehaviors();
        var classes=config.getEntityClasses();
        var worlds=config.getWorlds();
        var gamemodes=config.getValidGameModes();
        this.plugin=plugin;
        for(var x: behaviors) {
            if(blockBehaviorMap.get(x.from())==null) blockBehaviorMap.put(x.from(),new ArrayList());
            blockBehaviorMap.get(x.from()).add(x);
        }
        var tempclasses=new Class[classes.size()];
        for(int i=0;i<classes.size();i++) {
            tempclasses[i]=classes.get(i);
        }
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin,() -> {
            for(var world:worlds) {
                world.getEntitiesByClasses(tempclasses).forEach((e) -> {
                    if(e instanceof Player) {
                        if(!gamemodes.contains(((Player) e).getGameMode())) return;
                        if(((Player) e).isSneaking()) return;
                    }
                    if(e instanceof LivingEntity) {
                        if(config.getLeatherBootsPreventTrampling()) {
                            var is=((LivingEntity) e).getEquipment().getBoots();
                            if(is!=null && is.getType()==Material.LEATHER_BOOTS) return;
                        }
                    }
                    var block=e.getLocation().add(0,-0.46,0).getBlock();
                    var blockBehaviors=blockBehaviorMap.get(block.getType());
                    if(blockBehaviors==null) return;
                    for(var blockBehavior:blockBehaviors) {

                        if(Math.random()>blockBehavior.chance()) continue;

                        block.setType(blockBehavior.to());
                    }


                });
            }
        },0,1);
    }
}
