package com.bigstickpolicies.troddenpath.tread;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockTreadSheet {
    Map<Material, List<BlockTreadBehavior>> treadMap=new HashMap();
    public BlockTreadSheet() {
    }
    public void add(Material m,BlockTreadBehavior data) {
        if(treadMap.get(m)==null) treadMap.put(m,new ArrayList());
        treadMap.get(m).add(data);
    }
    public List<BlockTreadBehavior> get(Material m) {
        if(treadMap.get(m)==null) return new ArrayList();
        return treadMap.get(m);
    }
}
