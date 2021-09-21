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
    private BootFunctions bootfuncs=BootFunctions.get();
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
        register(tramplers,bootfuncs::tramplers);
        newRecipe("tramplers",tramplers,"CCC","CKC","CCC");
        recipe.setIngredient('K',Material.CHAINMAIL_BOOTS);
        recipe.setIngredient('C',Material.CHAIN);
        finishRecipe();


        //#####Scortchers
        var scorchers=new ItemBuilder(Material.NETHERITE_BOOTS).chatcolor(ChatColor.GOLD+"")
                .loreSplitLines("Leaves trails on netherrack, nylium, and soul sand").name(ChatColor.RED+"Scorchers")
                        .ench(Enchantment.FIRE_ASPECT,1).build();

        register(scorchers,bootfuncs::scorchers);

        newRecipe("scorchers",scorchers,"CCC","CBC","CCC");

        recipe.setIngredient('C',Material.FIRE_CHARGE);
        recipe.setIngredient('B',Material.NETHERITE_BOOTS);
        finishRecipe();

        var titans=new ItemBuilder(Material.DIAMOND_BOOTS).chatcolor(ChatColor.BLUE+"")
                .loreSplitLines("Very heavy boots that can compress stone.").name(ChatColor.AQUA+"Titan Boots")
                .ench(Enchantment.PROTECTION_ENVIRONMENTAL,5)
                .ench(Enchantment.DURABILITY,4).build();
        register(titans,bootfuncs::titans);
        newRecipe("titan_boots",titans,"A A","A A");
        recipe.setIngredient('A',Material.DIAMOND_BLOCK);
        finishRecipe();

        var seedlayers=new LeatherArmorBuilder(Material.LEATHER_BOOTS).color(Color.YELLOW).
                name(ChatColor.GOLD+"Seedlayer Boots").chatcolor(ChatColor.BLUE+"")
                .loreSplitLines("A nifty marvel that automatically lays seeds").build();

        register(seedlayers,bootfuncs::seedlayers);
        newRecipe("seedlayers",seedlayers,"HLH","IYI");
        recipe.setIngredient('I',Material.DIAMOND_HOE);
        recipe.setIngredient('H',Material.HOPPER);
        recipe.setIngredient('L',Material.LEATHER_BOOTS);
        recipe.setIngredient('Y',Material.YELLOW_DYE);

        finishRecipe();



    }
}
