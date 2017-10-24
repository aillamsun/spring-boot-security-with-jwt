package com.chinawiserv.utils.excel;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wangt
 * Date: 17-4-1
 * Time: 上午11:39
 * To change this template use File | Settings | File Templates.
 */
public class DownloadDatas {

    public static Map<String,String> getShopMap(){
        Map<String,String> map = Maps.newLinkedHashMap();
        map.put("店铺名称","shop_name");
        return map;
    }

    public static Map<String,String> getCommentMap(){
        Map<String,String> map = Maps.newLinkedHashMap();
        map.put("内容","content");
        return map;
    }
}
