package com.caomao.genshin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "avatar")
public class AvatarEntity {

    @Id
    private Integer id;

    private Integer stay;

    private String name;

    private String element;

    private String weaponType;

    private String region;

    private String specialProp;

    private String bodyType;

    private String icon;

    // Getters and Setters
}
