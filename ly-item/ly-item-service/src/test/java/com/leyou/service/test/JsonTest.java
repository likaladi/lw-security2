package com.leyou.service.test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.leyou.common.util.JsonUtil;
import com.leyou.item.pojo.Specification;
import com.leyou.item.vo.SpecParam;

import java.util.ArrayList;
import java.util.List;

public class JsonTest {

    public static void main(String[] args) {
        Long cid = 76L;
        Boolean searching = true;
        Boolean generic = false;
        String specifications = "[{\"group\":\"主体\",\"params\":[{\"k\":\"品牌\",\"searchable\":false,\"global\":true,\"options\":[]},{\"k\":\"型号\",\"searchable\":false,\"global\":true,\"options\":[]},{\"k\":\"上市年份\",\"searchable\":false,\"global\":true,\"options\":[],\"numerical\":true,\"unit\":\"年\"}]},{\"group\":\"基本信息\",\"params\":[{\"k\":\"机身颜色\",\"searchable\":false,\"global\":false,\"options\":[]},{\"k\":\"机身重量（g）\",\"searchable\":false,\"global\":true,\"options\":[],\"numerical\":true,\"unit\":\"g\"},{\"k\":\"机身材质工艺\",\"searchable\":false,\"global\":true,\"options\":[]}]},{\"group\":\"操作系统\",\"params\":[{\"k\":\"操作系统\",\"searchable\":true,\"global\":true,\"options\":[\"安卓\",\"IOS\",\"Windows\",\"功能机\"]}]},{\"group\":\"主芯片\",\"params\":[{\"k\":\"CPU品牌\",\"searchable\":true,\"global\":true,\"options\":[\"骁龙（Snapdragon)\",\"麒麟\"]},{\"k\":\"CPU型号\",\"searchable\":false,\"global\":true,\"options\":[]},{\"k\":\"CPU核数\",\"searchable\":true,\"global\":true,\"options\":[\"一核\",\"二核\",\"四核\",\"六核\",\"八核\",\"十核\"]},{\"k\":\"CPU频率\",\"searchable\":true,\"global\":true,\"options\":[],\"numerical\":true,\"unit\":\"GHz\"}]},{\"group\":\"存储\",\"params\":[{\"k\":\"内存\",\"searchable\":true,\"global\":false,\"options\":[\"1GB及以下\",\"2GB\",\"3GB\",\"4GB\",\"6GB\",\"8GB\"],\"numerical\":false,\"unit\":\"\"},{\"k\":\"机身存储\",\"searchable\":true,\"global\":false,\"options\":[\"8GB及以下\",\"16GB\",\"32GB\",\"64GB\",\"128GB\",\"256GB\"],\"numerical\":false,\"unit\":\"\"}]},{\"group\":\"屏幕\",\"params\":[{\"k\":\"主屏幕尺寸（英寸）\",\"searchable\":true,\"global\":true,\"options\":[],\"numerical\":true,\"unit\":\"英寸\"},{\"k\":\"分辨率\",\"searchable\":false,\"global\":true,\"options\":[]}]},{\"group\":\"摄像头\",\"params\":[{\"k\":\"前置摄像头\",\"searchable\":true,\"global\":true,\"options\":[],\"numerical\":true,\"unit\":\"万\"},{\"k\":\"后置摄像头\",\"searchable\":true,\"global\":true,\"options\":[],\"numerical\":true,\"unit\":\"万\"}]},{\"group\":\"电池信息\",\"params\":[{\"k\":\"电池容量（mAh）\",\"searchable\":true,\"global\":true,\"options\":[],\"numerical\":true,\"unit\":\"mAh\"}]}]";

        List<SpecParam> datas = new ArrayList<>();
        new JsonParser().parse(specifications).getAsJsonArray().forEach(arr -> {
            arr.getAsJsonObject().get("params").getAsJsonArray().forEach(arr2 -> {
                JsonObject jsonObject = arr2.getAsJsonObject();
                boolean searchable = jsonObject.get("searchable").getAsBoolean();
                boolean global = jsonObject.get("global").getAsBoolean();
                if(searching.equals(searchable) && generic.equals(global)){
                    SpecParam specParam = new SpecParam();
                    specParam.setCid(cid);
                    specParam.setSearching(searchable);
                    specParam.setGeneric(global);
                    specParam.setFiledName(jsonObject.get("k").getAsString());
                    datas.add(specParam);
                }
            });
        });

        datas.forEach(data -> {
            System.out.println(JsonUtil.beanToJSONString(data));
        });
    }
}
