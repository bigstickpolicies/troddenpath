package com.bigstickpolicies.troddenpath.items;

import com.bigstickpolicies.troddenpath.TroddenPath;
import com.bigstickpolicies.troddenpath.tread.BootsEffectHook;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.util.Vector;


public class ItemInitializer {
    private ShapedRecipe recipe;
    private boolean skip=false;
    public void register(ItemStack boots, BootsEffectHook hook) {
        TroddenPath.bootsRegistry.register(boots,hook);
    }
    public void newRecipe(String name,ItemStack result,String... shape) {
        skip=!TroddenPath.globalConfigs.getCraftEnabled(name);
        NamespacedKey key=new NamespacedKey(TroddenPath.plugin,name);
        recipe=new ShapedRecipe(key,result);
        recipe.shape(shape);
    }
    public void finishRecipe() {
        if(skip) {
            recipe=null;
            return;
        }
        Bukkit.addRecipe(recipe);
        recipe=null;
    }
    public void create() {
        register(new ItemBuilder(Material.LEATHER_BOOTS).build(),
                (block,data,effector) -> {
                    return;
                });
        var tramplers=new ItemBuilder(Material.CHAINMAIL_BOOTS)
                .name("Tramplers").lore(ChatColor.AQUA+"+150% Trample Speed").build();
        register(tramplers,(block,data,effector) -> {
            if(!TroddenPath.globalConfigs.isDestroyable(block.getLocation().add(0,1,0).getBlock().getType())) return;
            for(var part:data) {
                if(Math.random()>part.chance()*(2.5)) continue;
                block.setType(part.to());
                return;
            }
        });
        newRecipe("tramplers",tramplers,"CCC","CKC","CCC");
        recipe.setIngredient('K',Material.CHAINMAIL_BOOTS);
        recipe.setIngredient('C',Material.CHAIN);
        finishRecipe();


        //#####Scortchers
        var scorchers=new ItemBuilder(Material.NETHERITE_BOOTS).chatcolor(ChatColor.GOLD+"")
                .loreSplitLines("Leaves trails on netherrack, nylium, and soul sand").name(ChatColor.RED+"Scorchers")
                        .ench(Enchantment.FIRE_ASPECT,1).build();

        register(scorchers,(block,data,effector) -> {
            if(!TroddenPath.globalConfigs.isDestroyable(block.getLocation().add(0,1,0).getBlock().getType())) return;
            if(block.getType()==Material.NETHERRACK) {
                if(Math.random()<1.0/(20*1)) {
                    block.setType(Material.NETHER_BRICKS);
                    block.getLocation().getWorld().playSound(block.getLocation(), Sound.ITEM_FIRECHARGE_USE,0.2f,1.0f);
                    block.getLocation().getWorld().spawnParticle(Particle.LAVA,block.getLocation(),10);
                    return;
                }
                if(Math.random()<1.0/(20*3)) {
                    block.setType(Material.NETHER_BRICK_SLAB);
                    block.getLocation().getWorld().playSound(block.getLocation(), Sound.ITEM_FIRECHARGE_USE,0.2f,1.0f);
                    block.getLocation().getWorld().spawnParticle(Particle.LAVA,block.getLocation(),10);
                    return;
                }
            }
            if(block.getType()==Material.SOUL_SAND||block.getType()==Material.SOUL_SOIL) {
                Material set=null;
                if(Math.random()<1.0/(20*1)) {
                    set=Material.BASALT;
                }
                if(Math.random()<1.0/(20*2)) {
                    set=Material.POLISHED_BASALT;
                }
                if(Math.random()<1.0/(20*2)) {
                    set=Material.SMOOTH_BASALT;
                }
                if(set!=null) {
                    block.getLocation().getWorld().playSound(block.getLocation(), Sound.ITEM_FIRECHARGE_USE,0.2f,1.0f);
                    block.getLocation().getWorld().spawnParticle(Particle.LAVA,block.getLocation(),10);
                    Material finalSet = set;
                    if(block.getType()==Material.SOUL_SAND) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(TroddenPath.plugin,() -> {
                            effector.setVelocity(effector.getVelocity().add(new Vector(0.0,0.2,0.0)));
                        },3);

                    }
                    Bukkit.getScheduler().scheduleSyncDelayedTask(TroddenPath.plugin,() -> {
                        block.setType(finalSet);
                    },4);
                    return;
                }
            }
            if(block.getType()==Material.CRIMSON_NYLIUM || block.getType()==Material.WARPED_NYLIUM) {
                if(Math.random()<1.0/(20*1)) {
                    block.setType(Material.NETHERRACK);
                    block.getLocation().getWorld().playSound(block.getLocation(), Sound.ITEM_FIRECHARGE_USE,0.2f,1.0f);
                    block.getLocation().getWorld().spawnParticle(Particle.LAVA,block.getLocation(),10);
                    return;
                }

            }
            for(var part:data) {
                if(Math.random()>part.chance()) continue;
                block.setType(part.to());
                block.getLocation().getWorld().spawnParticle(Particle.LAVA,block.getLocation(),10);
                return;
            }
        });

        newRecipe("scorchers",scorchers,"CCC","CBC","CCC");

        recipe.setIngredient('C',Material.FIRE_CHARGE);
        recipe.setIngredient('B',Material.NETHERITE_BOOTS);
        finishRecipe();


    }
}
