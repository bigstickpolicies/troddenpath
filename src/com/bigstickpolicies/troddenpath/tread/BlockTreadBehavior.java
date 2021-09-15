package com.bigstickpolicies.troddenpath.tread;

import com.bigstickpolicies.troddenpath.TroddenPath;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public record BlockTreadBehavior(@NotNull Material from, @NotNull Material to, @NotNull double chance) {
    public void tread(Block b, Entity e) {
        if(Math.random()>chance) return;
        if(e instanceof LivingEntity) {
            if(TroddenPath.globalConfigs.getLeatherBootsPreventTrampling()) {
                var is=((LivingEntity) e).getEquipment().getBoots();
                if(is!=null && is.getType()==Material.LEATHER_BOOTS) return;
            }
        }
        b.setType(to);
    }
}
