package com.chinawiserv.utils;

import com.chinawiserv.utils.ztree.ZtreeNode;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * 本地内存工具
 * Created by sungang on 2017/5/5.
 */
public class RamUtils {


    private static RamUtils instance;

    private RamUtils() {
    }

    public static RamUtils getInstance() {
        if (instance == null) {
            instance = new RamUtils();
        }
        return instance;
    }


    private Map<Long, List<ZtreeNode>> erpCategoryMap = Maps.newConcurrentMap();

    public void setErpCategory(Long key, List<ZtreeNode> ztreeNodes) {
        erpCategoryMap.put(key, ztreeNodes);
    }

    public List<ZtreeNode> getErpCategory(Long key) {
        return erpCategoryMap.get(key);
    }

    public void removeErpCategory(Long key){
        erpCategoryMap.remove(key);

    }
}
