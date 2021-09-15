package com.bigstickpolicies.troddenpath.items;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomItemManager {
    private Map<String, Integer> toItemType=new HashMap();
    private int count=1;
    private String loreHash(ItemStack item) {
        var meta=item.getItemMeta();
        var total="";
        for(var s:meta.getLore()) {
            total+=s;
        }
        return total;
    }
    public void register(ItemStack item) {
        toItemType.put(loreHash(item),count);
        count++;
    }
    public boolean isEqual(ItemStack i1,ItemStack i2) {
        return (toItemType.get(i1)==toItemType.get(i2));
    }
}
