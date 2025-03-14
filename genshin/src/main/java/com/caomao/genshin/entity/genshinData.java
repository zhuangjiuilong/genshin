package com.caomao.genshin.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
public class genshinData {
    private int response;
    AvatarData data;


    @Data
    static class AvatarData {

        // id
        public int id;

        //星级
        private int rank;
        //角色名
        private String name;
        //元素
        private String element;
        //武器类型
        private String weaponType;
        //地区
        private String region;
        //攻击属性
        private String specialProp;
        //体型
        private String bodyType;
        //头像
        private String icon;

    }

    static class upgrade {
        private prop prop;
    }

    static class prop{
        private String propType;

    }

}
