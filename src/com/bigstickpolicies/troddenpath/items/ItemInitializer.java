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
                (block,effector) -> {
                    return;
                });
        var tramplers=new ItemBuilder(Material.CHAINMAIL_BOOTS)
                .name("Tramplers").lore(ChatColor.AQUA+"+150% Trample Speed").build();
        register(tramplers,(block,effector) -> {
            var data=TroddenPath.globalConfigs.getTreadBehavior("base").get(block.getType());
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

        register(scorchers,(block,effector) -> {
            if(!TroddenPath.globalConfigs.isDestroyable(block.getLocation().add(0,1,0).getBlock().getType())) return;
            var scorchBehavior=TroddenPath.globalConfigs.getTreadBehavior("scorchers").get(block.getType());
            for(var beh:scorchBehavior) {
                if(Math.random()<beh.chance()) {

                    if(block.getType()==Material.SOUL_SAND) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask((TroddenPath.plugin),() -> {
                            effector.setVelocity(effector.getVelocity().add(new Vector(0,0.2,0)));
                        },3);
                    }
                    Bukkit.getScheduler().scheduleSyncDelayedTask(TroddenPath.plugin,() -> {
                        block.setType(beh.to());
                        block.getLocation().getWorld().playSound(block.getLocation(), Sound.ITEM_FIRECHARGE_USE,0.2f,1.0f);
                        block.getLocation().getWorld().spawnParticle(Particle.LAVA,block.getLocation(),10);
                    },4);
                    return;
                }
            }
            var baseBehavior=TroddenPath.globalConfigs.getTreadBehavior("base").get(block.getType());
            for(var beh:baseBehavior) {
                if (Math.random() < beh.chance()) {
                    block.setType(beh.to());
                    block.getLocation().getWorld().spawnParticle(Particle.LAVA, block.getLocation(), 10);
                    return;
                }
            }
        });

        newRecipe("scorchers",scorchers,"CCC","CBC","CCC");

        recipe.setIngredient('C',Material.FIRE_CHARGE);
        recipe.setIngredient('B',Material.NETHERITE_BOOTS);
        finishRecipe();

        var titans=new ItemBuilder(Material.DIAMOND_BOOTS).chatcolor(ChatColor.BLUE+"")
                .loreSplitLines("Very heavy boots that can compress stone.").name(ChatColor.AQUA+"Titan Boots")
                .ench(Enchantment.PROTECTION_ENVIRONMENTAL,5)
                .ench(Enchantment.DURABILITY,4).build();
        register(titans,(block,effector) -> {
            var behaviors=TroddenPath.globalConfigs.getTreadBehavior("titan_boots").get(block.getType());
            for(var beh: behaviors) {
                if(Math.random()<beh.chance()) {
                    block.setType(beh.to());
                    block.getLocation().getWorld().playSound(block.getLocation(),Sound.BLOCK_STONE_BREAK,1.0f,1.0f);

                    block.getLocation().getWorld().spawnParticle(Particle.CRIT,block.getLocation(),10);
                }
            }
        });
        newRecipe("titan_boots",titans,"A A","A A");
        recipe.setIngredient('A',Material.DIAMOND_BLOCK);
        finishRecipe();


    }
}
