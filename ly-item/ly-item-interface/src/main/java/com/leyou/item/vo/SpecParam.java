package com.leyou.item.vo;

import lombok.Data;

@Data
public class SpecParam {
    /**
     * 规格组id
     */
    private Long groupId;
    /**
     * 商品分类ID
     */
    private Long cid;
    /**
     * 是否搜索
     */
    private Boolean searching;
    /**
     * 是否全局属性
     */
    private Boolean generic;
    /**
     *是否数值类型
     */
    private Boolean numeric;
    /**
     *  计量单位
     */
    private String unit;
    /**
     * 属性名称
     */
    private String filedName;
}
