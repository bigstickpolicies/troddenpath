package com.bigstickpolicies.troddenpath.tread;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.List;

public interface BootsEffectHook {
    void change(Block block, Entity effector);
}
