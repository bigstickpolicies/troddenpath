package com.bigstickpolicies.troddenpath.tread;

import com.bigstickpolicies.troddenpath.TroddenPath;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public record BlockTreadBehavior(@NotNull Material to, @NotNull double chance) {
}
