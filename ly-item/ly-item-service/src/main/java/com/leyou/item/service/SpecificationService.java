package com.leyou.item.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.leyou.item.mapper.SpecificationMapper;
import com.leyou.item.pojo.Specification;
import com.leyou.item.vo.SpecParam;
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
        return datas;
    }

    public static void main(String[] args) {
        String json = "[{\"username\":\"test\"},{\"username\":\"test2\"}]";
        JsonArray arrs = new JsonParser().parse(json).getAsJsonArray();
        System.out.println(arrs.size());
    }
}
