package com.leyou.item.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.leyou.item.mapper.SpecificationMapper;
import com.leyou.item.pojo.Specification;
import com.leyou.item.vo.SpecGroup;
import com.leyou.item.vo.SpecParam;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecificationService<main> {

    @Autowired
    private SpecificationMapper specificationMapper;

    public Specification queryById(Long id) {
        return this.specificationMapper.selectByPrimaryKey(id);
    }
    public List<SpecParam> querySpecParams(Long gid, Long cid, Boolean searching, Boolean generic){
        List<SpecParam> datas = new ArrayList<>();
        Specification specification  = this.specificationMapper.selectByPrimaryKey(cid);
        String specifications = specification.getSpecifications();
        new JsonParser().parse(specifications).getAsJsonArray().forEach(arr -> {
           arr.getAsJsonObject().get("params").getAsJsonArray().forEach(arr2 -> {
               JsonObject jsonObject = arr2.getAsJsonObject();
               boolean searchable = jsonObject.get("searchable").getAsBoolean();
               boolean global = jsonObject.get("global").getAsBoolean();

               if(searching != null && generic == null){
                   if(searching.equals(searchable)){
                       datas.add(getSpecParam(cid,searchable,global,jsonObject));
                   }
               }
               if(generic != null && searching == null){
                   if(generic.equals(global)){
                       datas.add(getSpecParam(cid,searchable,global,jsonObject));
                   }
               }
               if(searching != null && generic != null){
                   if(searching.equals(searchable) && generic.equals(global)){
                       datas.add(getSpecParam(cid,searchable,global,jsonObject));
                   }
               }
           });
        });
        return datas;
    }



    private SpecParam getSpecParam(Long cid, boolean searchable, boolean global,JsonObject jsonObject){
        String options = jsonObject.get("options").toString();
        SpecParam specParam = new SpecParam();
        specParam.setCid(cid);
        specParam.setSearching(searchable);
        specParam.setGeneric(global);
        specParam.setFiledName(jsonObject.get("k").getAsString());
        specParam.setOptions(options);
        if(jsonObject.get("numerical") != null){
            specParam.setNumeric(jsonObject.get("numerical").getAsBoolean());
        }
        if(jsonObject.get("unit") != null){
            specParam.setUnit(jsonObject.get("unit").getAsString());
        }
        return specParam;
    }

    public List<SpecGroup> querySpecsByCid(Long cid) {
        Specification specification = specificationMapper.selectByPrimaryKey(cid);
        // 查询规格组
        List<SpecGroup> groups = new ArrayList<>();

        new JsonParser().parse(specification.getSpecifications()).getAsJsonArray().forEach(arr -> {
            String groupName = arr.getAsJsonObject().get("group").getAsString();
        });

        SpecParam param = new SpecParam();
        groups.forEach(g -> {
            // 查询组内参数
            g.setParams(this.querySpecParams(g.getId(), null, null, null));
        });
        return groups;
    }


    public static void main(String[] args) {
        String options = "{\"options\":[\"10-20\",\"21-30\"]}";
        String str = new JsonParser().parse(options).getAsJsonObject().get("options").toString();
        JsonArray jsonArray =  new JsonParser().parse(str).getAsJsonArray();
        for(JsonElement arr : jsonArray){
            String temp = arr.getAsString();
            System.out.println(temp);


        }

    }
}
