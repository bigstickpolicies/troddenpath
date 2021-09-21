package com.bigstickpolicies.troddenpath.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public class PlayerInventoryUtil {
    public static BiFunction<ItemStack, ItemStack, Boolean> completeEquality = (i, u) -> (i.getItemMeta().equals(u.getItemMeta()) && i.getType() == u.getType());

    public static int countItem(Player target, ItemStack reference) {
        return countItem(target, reference, completeEquality);
    }

    public static int countItem(Player target, ItemStack reference, BiFunction<ItemStack, ItemStack, Boolean> isEqual) {
        int numHasFound = 0;
        for (ItemStack i : target.getInventory()) {
            if (i == null || i.getItemMeta() == null) {
                continue;
            }
            if (isEqual.apply(i, reference)) {
                numHasFound += i.getAmount();

            }
        }
        for (ItemStack i : target.getOpenInventory().getTopInventory()) {
            if (i == null || i.getItemMeta() == null) {
                continue;
            }
            if (isEqual.apply(i, reference)) {
                numHasFound += i.getAmount();

            }
        }
        ItemStack i = target.getItemOnCursor();
        if (i != null && i.getItemMeta() != null) {
            if (isEqual.apply(i, reference)) {
                numHasFound += i.getAmount();

            }
        }
        return numHasFound;
    }

    public static void removeItem(Player target, ItemStack reference, int amount) {
        removeItem(target, reference, amount, completeEquality);
    }

    public static void removeItem(Player target, ItemStack reference, int amount, BiFunction<ItemStack, ItemStack, Boolean> isEqual) {
        for (ItemStack i : target.getInventory()) {
            if (i == null || i.getItemMeta() == null) {
                continue;
            }
            if (isEqual.apply(i, reference)) {
                if (i.getAmount() > amount) {
                    i.setAmount(i.getAmount() - amount);
                    return;
                }
                amount -= i.getAmount();
                i.setAmount(0);
            }
        }
        for (ItemStack i : target.getOpenInventory().getTopInventory()) {
            if (i == null || i.getItemMeta() == null) {
                continue;
            }
            if (isEqual.apply(i, reference)) {
                if (i.getAmount() > amount) {
                    i.setAmount(i.getAmount() - amount);
                    return;
                }
                amount -= i.getAmount();
                i.setAmount(0);
            }
        }
        ItemStack i = target.getItemOnCursor();
        if (i != null && i.getItemMeta() != null) {
            if (isEqual.apply(i, reference)) {
                if (i.getAmount() > amount) {
                    i.setAmount(i.getAmount() - amount);
                    return;
                }
                amount -= i.getAmount();
                i.setAmount(0);
            }
        }
    }

    public static ItemStack getItemStack(Material type, Player target) {
        for (ItemStack i : target.getInventory()) {
            if (i == null || i.getItemMeta() == null) {
                continue;
            }
            if (i.getType() == type) {
                return i;
            }
        }
        for (ItemStack i : target.getOpenInventory().getTopInventory()) {
            if (i == null || i.getItemMeta() == null) {
                continue;
            }
            if (i.getType() == type) {
                return i;
            }
        }
        ItemStack i = target.getItemOnCursor();
        if (i == null || i.getItemMeta() == null) {
            return null;
        }
        if (i.getType() == type) {
            return i;
        }
        return null;
    }

    public static void apply(Player target, Consumer<ItemStack> applier) {
        for (ItemStack i : target.getInventory()) {
            if (i == null || i.getItemMeta() == null) {
                continue;
            }
            applier.accept(i);
        }
        for (ItemStack i : target.getOpenInventory().getTopInventory()) {
            if (i == null || i.getItemMeta() == null) {
                continue;
            }
            applier.accept(i);
        }
        ItemStack i = target.getItemOnCursor();
        if (i != null && i.getItemMeta() != null) {
            applier.accept(i);
        }
    }
}
