package com.bigstickpolicies.troddenpath.tread;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public record BlockTreadBehavior(@NotNull Material from, @NotNull Material to, @NotNull double chance) {
}
