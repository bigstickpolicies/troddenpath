package com.bigstickpolicies.troddenpath.items;

import com.bigstickpolicies.troddenpath.TroddenPath;
import com.bigstickpolicies.troddenpath.util.PlayerInventoryUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BootFunctions {
    public static BootFunctions singleton;
    public Map<Material,Material> seeds;
    public BootFunctions() {
        seeds=new HashMap();
        seeds.put(Material.WHEAT_SEEDS,Material.WHEAT);
        seeds.put(Material.CARROT,Material.CARROTS);
        seeds.put(Material.MELON_SEEDS,Material.MELON_STEM);
        seeds.put(Material.PUMPKIN_SEEDS,Material.PUMPKIN_STEM);
        seeds.put(Material.POTATO,Material.POTATOES);
        seeds.put(Material.BEETROOT_SEEDS,Material.BEETROOTS);
    }
    public static BootFunctions get() {
        if(singleton==null) {
            singleton=new BootFunctions();
        }
        return singleton;
    }
    public void base(Block block, Entity effector) {
        var data= TroddenPath.globalConfigs.getTreadBehavior("base").get(block.getType());
        if(!TroddenPath.globalConfigs.isDestroyable(block.getLocation().add(0,1,0).getBlock().getType())) return;
        for(var part:data) {
            if(Math.random()>part.chance()) continue;
            block.setType(part.to());
            continue;
        }
    }
    public void tramplers(Block block, Entity effector) {
        var data= TroddenPath.globalConfigs.getTreadBehavior("base").get(block.getType());
        if(!TroddenPath.globalConfigs.isDestroyable(block.getLocation().add(0,1,0).getBlock().getType())) return;
        for(var part:data) {
            if(Math.random()>part.chance()*(2.5)) continue;
            block.setType(part.to());
            continue;
        }
    }
    public void scorchers(Block block,Entity effector) {
        if(!TroddenPath.globalConfigs.isDestroyable(block.getLocation().add(0,1,0).getBlock().getType())) return;
        var scorchBehavior=TroddenPath.globalConfigs.getTreadBehavior("scorchers").get(block.getType());
        for(var beh:scorchBehavior) {
            if(Math.random()<beh.chance()) {

                if(block.getType()== Material.SOUL_SAND) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask((TroddenPath.plugin),() -> {
                        effector.setVelocity(effector.getVelocity().add(new Vector(0,0.2,0)));
                    },3);
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(TroddenPath.plugin,() -> {
                    block.setType(beh.to());
                    block.getLocation().getWorld().playSound(block.getLocation(), Sound.ITEM_FIRECHARGE_USE,0.2f,1.0f);
                    block.getLocation().getWorld().spawnParticle(Particle.LAVA,block.getLocation(),10);
                },4);
                continue;
            }
        }
        var baseBehavior=TroddenPath.globalConfigs.getTreadBehavior("base").get(block.getType());
        for(var beh:baseBehavior) {
            if (Math.random() < beh.chance()) {
                block.setType(beh.to());
                block.getLocation().getWorld().spawnParticle(Particle.LAVA, block.getLocation(), 10);
                continue;
            }
        }
    }
    public void titans(Block block,Entity effector) {
        if(!TroddenPath.globalConfigs.isDestroyable(block.getLocation().add(0,1,0).getBlock().getType())) return;
        var behaviors=TroddenPath.globalConfigs.getTreadBehavior("titan_boots").get(block.getType());
        for(var beh: behaviors) {
            if(Math.random()<beh.chance()) {
                block.setType(beh.to());
                block.getLocation().getWorld().playSound(block.getLocation(),Sound.BLOCK_STONE_BREAK,1.0f,1.0f);

                block.getLocation().getWorld().spawnParticle(Particle.CRIT,block.getLocation(),10);
            }
        }
        var baseBehavior=TroddenPath.globalConfigs.getTreadBehavior("base").get(block.getType());
        for(var beh:baseBehavior) {
            if (Math.random() < beh.chance()) {
                block.setType(beh.to());
                continue;
            }
        }
    }

    public void seedlayers(Block block,Entity effector) {
        if(!TroddenPath.globalConfigs.isDestroyable(block.getLocation().add(0,1,0).getBlock().getType())) return;
        var behaviors=TroddenPath.globalConfigs.getTreadBehavior("seedlayers").get(block.getType());
        if(!(effector instanceof Player)) {
            return;
        }

        for(var beh: behaviors) {
            if (Math.random() < beh.chance()) {


                block.setType(beh.to());

                block.getLocation().getWorld().playSound(block.getLocation(), Sound.ITEM_CROP_PLANT, 1.0f, 1.0f);
            }
        }
        if(!(block.getType()==Material.FARMLAND)) return;
        var player=(Player) effector;
        ItemStack slot=null;
        for(var i=0;i<9;i++) {
            var is=player.getInventory().getItem(i);
            if(is==null) continue;
            if(seeds.containsKey(is.getType())) {
                slot=is;
                break;
            };
        }
        if(slot==null) return;
        var b2 = block.getRelative(0, 1, 0);
        b2.setType(seeds.get(slot.getType()));
        slot.setAmount(slot.getAmount()-1);

    }
}
