package com.chinawiserv.utils;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by sungang on 2017/4/26.
 */
public class GoodsUpConditionUtils {



    public static List<Integer> getGoodsUpCondition(){
        List<Integer> upConditions = Lists.newArrayList();
        upConditions.add(0);
        upConditions.add(1);
        upConditions.add(2);
        upConditions.add(6);
        upConditions.add(8);
        return upConditions;
    }
}
