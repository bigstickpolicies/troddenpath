package com.bigstickpolicies.troddenpath.items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class LeatherArmorBuilder extends ItemBuilder {
    public LeatherArmorBuilder(Material material) {
        super(material);
    }

    public LeatherArmorBuilder color(Color c) {
        ((LeatherArmorMeta) meta).setColor(c);
        return this;
    }
}
