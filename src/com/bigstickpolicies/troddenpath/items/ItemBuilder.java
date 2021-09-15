package com.bigstickpolicies.troddenpath.items;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

public class ItemBuilder {
    ItemStack is;
    ItemMeta meta;
    List<String> lore = new ArrayList();
    String chatcolor;

    public ItemBuilder(Material material) {
        is = new ItemStack(material);
        meta = is.getItemMeta();
        chatcolor = "";
    }

    public ItemBuilder(ItemStack is) {
        this.is = is;
        meta = is.getItemMeta();
        lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList();
        }
        chatcolor = "";
    }

    public ItemBuilder name(String s) {
        meta.setDisplayName("§f" + s);
        return this;
    }

    public ItemBuilder ench(Enchantment e, int level) {
        meta.addEnchant(e, level, true);
        return this;
    }

    public ItemBuilder bind() {
        return ench(Enchantment.BINDING_CURSE, 1);
    }

    public ItemBuilder unbreakable(boolean b) {
        meta.setUnbreakable(b);
        return this;
    }

    public ItemBuilder unbreak() {
        return unbreakable(true);
    }

    public ItemBuilder size(int s) {
        is.setAmount(s);
        return this;
    }

    public ItemBuilder attrib(Attribute at, double val) {
        return attrib(at, val, "hand");
    }

    public ItemBuilder attrib(Attribute at, double val, String slot) {
        meta.removeAttributeModifier(at);
        EquipmentSlot equipmentSlot = EquipmentSlot.HAND;
        if (slot.equals("feet")) {
            equipmentSlot = EquipmentSlot.FEET;
        }
        if (slot.equals("chest")) {
            equipmentSlot = EquipmentSlot.LEGS;
        }
        if (slot.equals("head")) {
            equipmentSlot = EquipmentSlot.HEAD;
        }
        if (slot.equals("legs")) {
            equipmentSlot = EquipmentSlot.LEGS;
        }
        if (slot.equals("hand")) {
            equipmentSlot = EquipmentSlot.HAND;
        }
        if (slot.equals("offhand")) {
            equipmentSlot = EquipmentSlot.OFF_HAND;
        }
        meta.addAttributeModifier(at, new AttributeModifier(UUID.randomUUID(), "idc", val, AttributeModifier.Operation.ADD_NUMBER, equipmentSlot));
        return this;
    }

    public ItemBuilder attackDamage(double k) {
        return attrib(Attribute.GENERIC_ATTACK_DAMAGE, k - 1);
    }

    public ItemBuilder attackSpeed(double k) {
        return attrib(Attribute.GENERIC_ATTACK_SPEED, k - 4);
    }

    public ItemBuilder kbRes(double res) {
        return attrib(Attribute.GENERIC_KNOCKBACK_RESISTANCE, res);
    }

    public ItemBuilder kbRes(double res, String slot) {
        return attrib(Attribute.GENERIC_KNOCKBACK_RESISTANCE, res, slot);
    }

    public ItemBuilder movementSpeed(double amount) {
        return attrib(Attribute.GENERIC_MOVEMENT_SPEED, amount);
    }

    public ItemBuilder movementSpeed(double amount, String slot) {
        return attrib(Attribute.GENERIC_MOVEMENT_SPEED, amount, slot);
    }

    public ItemBuilder addLore(String s) {
        lore.add(chatcolor + s);
        return this;
    }

    public ItemBuilder lore(String s) {
        return addLore(s);
    }

    public ItemBuilder loreSplitLines(String s) {
        StringTokenizer st = new StringTokenizer(s);
        int length = 0;
        String prev = "";
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            length += token.length() + 1;
            if (length > 25) {
                lore.add(chatcolor + prev);
                prev = "";
                length = 0;
            }
            prev += token + " ";
        }
        lore.add(chatcolor + prev);
        return this;
    }

    public ItemStack build() {
        if (lore.size() != 0) {
            meta.setLore(lore);
        }
        is.setItemMeta(meta);
        return is;
    }

    public ItemBuilder chatcolor(String s) {
        chatcolor = s;
        return this;
    }


}
