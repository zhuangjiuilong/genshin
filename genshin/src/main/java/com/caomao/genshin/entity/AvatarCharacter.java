package com.caomao.genshin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 原神角色信息表实体类
 * 对应表：avatar_character
 */
@Data // Lombok 注解，自动生成getter/setter/toString/equals/hashCode
@TableName("avatar_character") // MyBatis-Plus 注解，指定映射的数据库表名
public class AvatarCharacter {

    /**
     * 角色 ID（主键）
     * IdType.AUTO：主键自增（若表中id未设置auto_increment，需改为IdType.INPUT，手动传入id）
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 角色名称
     */
    @TableField("name") // 字段名与属性名一致时，可省略该注解，这里为了演示保留
    @JsonProperty(defaultValue = "")
    private String name;

    /**
     * 称号
     */
    @JsonProperty(defaultValue = "")
    private String title;

    /**
     * 描述
     */
    @JsonProperty(defaultValue = "")
    private String description;

    /**
     * 武器类型 (WEAPON_CLAYMORE 等)
     */
    @TableField("weapon_type") // 数据库字段是下划线，实体是驼峰，MyBatis-Plus 默认驼峰转下划线，也可省略
    @JsonProperty(defaultValue = "")
    private String weaponType;

    /**
     * 武器文本 (双手剑)
     */
    @TableField("weapon_text")
    @JsonProperty(defaultValue = "")
    private String weaponText;

    /**
     * 体型 (BODY_LOLI 等)
     */
    @TableField("body_type")
    @JsonProperty(defaultValue = "")
    private String bodyType;

    /**
     * 性别
     */
    @JsonProperty(defaultValue = "")
    private String gender;

    /**
     * 品质类型 (QUALITY_PURPLE 等)
     */
    @TableField("quality_type")
    @JsonProperty(defaultValue = "")
    private String qualityType;

    /**
     * 星级 (4 星、5 星)
     */
    @JsonProperty(defaultValue = "")
    private Integer rarity;

    /**
     * 生日 MM/DD 格式
     */
    @JsonProperty(defaultValue = "")
    @TableField("birthday_mmdd")
    private String birthdayMmdd;

    /**
     * 生日文本
     */
    @JsonProperty(defaultValue = "")
    private String birthday;

    /**
     * 元素类型 (ELEMENT_HYDRO 等)
     */
    @TableField("element_type")
    @JsonProperty(defaultValue = "")
    private String elementType;

    /**
     * 元素文本 (水、火等)
     */
    @TableField("element_text")
    @JsonProperty(defaultValue = "")
    private String elementText;

    /**
     * 所属势力
     */
    @JsonProperty(defaultValue = "")
    private String affiliation;

    /**
     * 关联类型
     */
    @JsonProperty(defaultValue = "")
    @TableField("association_type")
    private String associationType;

    /**
     * 地区
     */
    @JsonProperty(defaultValue = "")
    private String region;

    /**
     * 副属性类型
     */
    @JsonProperty(defaultValue = "")
    @TableField("substat_type")
    private String substatType;

    /**
     * 副属性文本
     */
    @JsonProperty(defaultValue = "")
    @TableField("substat_text")
    private String substatText;

    /**
     * 命之座名称
     */
    @JsonProperty(defaultValue = "")
    private String constellation;

    /**
     * 英文配音
     */
    @JsonProperty(defaultValue = "")
    @TableField("cv_english")
    private String cvEnglish;

    /**
     * 中文配音
     */
    @JsonProperty(defaultValue = "")
    @TableField("cv_chinese")
    private String cvChinese;

    /**
     * 日文配音
     */
    @JsonProperty(defaultValue = "")
    @TableField("cv_japanese")
    private String cvJapanese;

    /**
     * 韩文配音
     */
    @JsonProperty(defaultValue = "")
    @TableField("cv_korean")
    private String cvKorean;
}
