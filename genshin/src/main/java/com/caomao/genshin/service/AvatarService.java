package com.caomao.genshin.service;

import com.caomao.genshin.entity.AvatarEntity;
import com.caomao.genshin.repository.AvatarRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;

    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    public String fetchAndStoreAvatarData(String id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://gi.yatta.moe/api/v2/chs/avatar/" + id + "?vh=54F0";

        try {
            // 发送GET请求并获取响应
            genshinData response = restTemplate.getForObject(url,  genshinData.class);
            log.info("Response: {}", response);
            if (response != null && response.getResponse()  == 200) {
                AvatarEntity entity = getAvatarEntity(response);


                avatarRepository.save(entity);
                return "数据获取并存储成功";
            }
        } catch (Exception e) {
            System.out.println("Failed  to fetch or store data: " + e.getMessage());
        }
        return "数据获取失败";
    }

    private static AvatarEntity getAvatarEntity(genshinData response) {
        AvatarEntity entity = new AvatarEntity();
        entity.setId(response.getData().getId());
        entity.setStay(response.getData().getRank());
        entity.setName(response.getData().getName());
        entity.setElement(response.getData().getElement());
        entity.setWeaponType(response.getData().getWeaponType());
        entity.setRegion(response.getData().getRegion());
        entity.setSpecialProp(response.getData().getSpecialProp());
        entity.setBodyType(response.getData().getBodyType());
        entity.setIcon(response.getData().getIcon());
        return entity;
    }

    @Data
    public static class genshinData {
        private int response;
        private AvatarData data;


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
        }

        static class prop{
            private String propType;

        }

    }
}
